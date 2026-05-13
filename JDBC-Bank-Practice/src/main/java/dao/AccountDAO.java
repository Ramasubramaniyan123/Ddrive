package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.DBConfig;
import exception.AccountNotFoundException;
import model.Account;
import model.Account.AccountType;

/**
 * AccountDAO — Data Access Object for the 'accounts' table.
 *
 * This DAO handles all account-related database operations.
 *
 * ============================================================
 * TRANSACTION DESIGN NOTE
 * ============================================================
 * Some methods in this DAO accept a Connection parameter.
 * This is the "connection passing" pattern for transactions.
 *
 * When a service method needs to perform multiple DAO operations
 * atomically (e.g., deduct from one account AND credit another),
 * it:
 *   1. Gets a connection from the pool
 *   2. Sets autoCommit = false
 *   3. Passes the connection to each DAO method
 *   4. Commits or rolls back
 *
 * If each DAO method got its own connection, they would be on
 * separate transactions and could not be rolled back together.
 *
 * Methods with a Connection parameter: part of a larger transaction
 * Methods without a Connection parameter: self-contained transaction
 */
public class AccountDAO {

    private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

    // ----------------------------------------------------------------
    // SQL Constants
    // ----------------------------------------------------------------

    private static final String SQL_INSERT =
        "INSERT INTO accounts (customer_id, account_number, account_type, balance) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID =
        "SELECT account_id, customer_id, account_number, account_type, balance, created_at " +
        "FROM accounts WHERE account_id = ?";

    private static final String SQL_FIND_BY_NUMBER =
        "SELECT account_id, customer_id, account_number, account_type, balance, created_at " +
        "FROM accounts WHERE account_number = ?";

    private static final String SQL_FIND_BY_CUSTOMER =
        "SELECT account_id, customer_id, account_number, account_type, balance, created_at " +
        "FROM accounts WHERE customer_id = ? ORDER BY created_at DESC";

    private static final String SQL_UPDATE_BALANCE =
        "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

    private static final String SQL_GET_BALANCE =
        "SELECT balance FROM accounts WHERE account_number = ? FOR UPDATE";

    private static final String SQL_DELETE =
        "DELETE FROM accounts WHERE account_id = ?";

    private static final String SQL_FIND_ALL_SAVINGS =
        "SELECT account_id, customer_id, account_number, account_type, balance, created_at " +
        "FROM accounts WHERE account_type = 'SAVINGS'";

    private static final String SQL_UPDATE_BALANCE_DIRECT =
        "UPDATE accounts SET balance = ? WHERE account_number = ?";

    // ----------------------------------------------------------------
    // Insert
    // ----------------------------------------------------------------

    /**
     * Inserts a new account into the database.
     *
     * @param account the account to insert
     * @return the inserted Account with accountId populated
     */
    public Account insert(Account account) {
        logger.debug("Inserting account for customer ID: {}", account.getCustomerId());

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setString(3, account.getAccountType().name());
            // setBigDecimal preserves exact decimal precision
            ps.setBigDecimal(4, account.getBalance());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Account insert failed — no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    account.setAccountId(keys.getInt(1));
                }
            }

            logger.info("Account created: {} (ID: {})",
                account.getAccountNumber(), account.getAccountId());
            return account;

        } catch (SQLException e) {
            logger.error("Failed to insert account: {}", e.getMessage());
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------
    // Find operations
    // ----------------------------------------------------------------

    /**
     * Finds an account by its primary key.
     */
    public Optional<Account> findById(int accountId) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding account by ID {}: {}", accountId, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    /**
     * Finds an account by its account number.
     * This is the most common lookup — account numbers are user-facing identifiers.
     */
    public Optional<Account> findByAccountNumber(String accountNumber) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NUMBER)) {

            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding account {}: {}", accountNumber, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    /**
     * Finds all accounts belonging to a specific customer.
     */
    public List<Account> findByCustomerId(int customerId) {
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CUSTOMER)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding accounts for customer {}: {}", customerId, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return accounts;
    }

    /**
     * Retrieves all SAVINGS accounts (used for interest calculation).
     */
    public List<Account> findAllSavingsAccounts() {
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL_SAVINGS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error fetching savings accounts: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return accounts;
    }

    // ----------------------------------------------------------------
    // Balance operations (used within transactions — accept Connection)
    // ----------------------------------------------------------------

    /**
     * Adjusts an account's balance by a delta amount (positive or negative).
     *
     * Uses "balance = balance + ?" instead of "balance = ?" to avoid
     * lost update problems in concurrent environments.
     *
     * This method accepts an external Connection so it can participate
     * in a larger transaction managed by the service layer.
     *
     * @param conn          the connection (managed by the caller's transaction)
     * @param accountNumber the account to update
     * @param delta         positive to credit, negative to debit
     * @throws SQLException if the update fails
     */
    public void adjustBalance(Connection conn, String accountNumber,
                              BigDecimal delta) throws SQLException {
        logger.debug("Adjusting balance for {}: delta={}", accountNumber, delta);

        try (PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BALANCE)) {
            ps.setBigDecimal(1, delta);
            ps.setString(2, accountNumber);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new AccountNotFoundException(accountNumber);
            }
        }
    }

    /**
     * Reads the current balance of an account with a row-level lock.
     *
     * "SELECT ... FOR UPDATE" acquires an exclusive lock on the row.
     * This prevents another transaction from modifying the balance
     * between our read and our subsequent update — preventing race conditions.
     *
     * Must be called within an active transaction (autoCommit = false).
     *
     * @param conn          the connection (managed by the caller's transaction)
     * @param accountNumber the account to read
     * @return the current balance
     * @throws SQLException if the account is not found or query fails
     */
    public BigDecimal getBalanceForUpdate(Connection conn,
                                          String accountNumber) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_GET_BALANCE)) {
            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
                throw new AccountNotFoundException(accountNumber);
            }
        }
    }

    /**
     * Sets an account's balance to a specific value.
     * Used for interest calculation updates.
     *
     * @param conn          the connection (managed by the caller's transaction)
     * @param accountNumber the account to update
     * @param newBalance    the new balance value
     * @throws SQLException if the update fails
     */
    public void setBalance(Connection conn, String accountNumber,
                           BigDecimal newBalance) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BALANCE_DIRECT)) {
            ps.setBigDecimal(1, newBalance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        }
    }

    // ----------------------------------------------------------------
    // Delete
    // ----------------------------------------------------------------

    /**
     * Deletes an account by its primary key.
     * The service layer must verify balance = 0 before calling this.
     *
     * @param accountId the account to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int accountId) {
        logger.debug("Deleting account ID: {}", accountId);

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, accountId);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error deleting account {}: {}", accountId, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------
    // Batch insert (for demo data)
    // ----------------------------------------------------------------

    /**
     * Batch inserts multiple accounts.
     * Demonstrates JDBC batch processing for bulk operations.
     *
     * @param accounts list of accounts to insert
     * @param conn     the connection (for transaction control by caller)
     * @throws SQLException if any insert fails
     */
    public void batchInsert(List<Account> accounts, Connection conn) throws SQLException {
        logger.info("Batch inserting {} accounts", accounts.size());

        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            for (Account account : accounts) {
                ps.setInt(1, account.getCustomerId());
                ps.setString(2, account.getAccountNumber());
                ps.setString(3, account.getAccountType().name());
                ps.setBigDecimal(4, account.getBalance());
                ps.addBatch(); // Queue this row
            }
            ps.executeBatch(); // Send all rows in one network call
            logger.info("Batch account insert completed.");
        }
    }

    // ----------------------------------------------------------------
    // Private helper: ResultSet → Account mapping
    // ----------------------------------------------------------------

    private Account mapRow(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setCustomerId(rs.getInt("customer_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAccountType(AccountType.fromString(rs.getString("account_type")));
        account.setBalance(rs.getBigDecimal("balance"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            account.setCreatedAt(ts.toLocalDateTime());
        }

        return account;
    }
}
