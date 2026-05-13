package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.DBConfig;
import model.Transaction;
import model.Transaction.TransactionType;

/**
 * TransactionDAO — Data Access Object for the 'transactions' table.
 *
 * Transactions are append-only (INSERT only, no UPDATE or DELETE).
 * This is a fundamental principle of financial systems — the transaction
 * log is an immutable audit trail.
 *
 * ============================================================
 * DESIGN NOTE: Connection Passing for Atomicity
 * ============================================================
 * The insert() method accepts a Connection parameter.
 *
 * Why? Consider a fund transfer:
 *   1. Deduct from sender's account (AccountDAO)
 *   2. Credit receiver's account (AccountDAO)
 *   3. Log the transaction (TransactionDAO)
 *
 * All three steps MUST succeed or ALL must fail.
 * If we used separate connections, step 3 could fail while
 * steps 1 and 2 already committed — money would disappear.
 *
 * By passing the same Connection to all three DAO calls,
 * they all participate in the same database transaction.
 * The service layer controls commit/rollback.
 */
public class TransactionDAO {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);

    // ----------------------------------------------------------------
    // SQL Constants
    // ----------------------------------------------------------------

    private static final String SQL_INSERT =
        "INSERT INTO transactions " +
        "(from_account, to_account, transaction_type, amount, remarks) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ACCOUNT =
        "SELECT transaction_id, from_account, to_account, transaction_type, " +
        "       amount, transaction_time, remarks " +
        "FROM transactions " +
        "WHERE from_account = ? OR to_account = ? " +
        "ORDER BY transaction_time DESC";

    private static final String SQL_FIND_ALL =
        "SELECT transaction_id, from_account, to_account, transaction_type, " +
        "       amount, transaction_time, remarks " +
        "FROM transactions " +
        "ORDER BY transaction_time DESC " +
        "LIMIT ?";

    private static final String SQL_FIND_BY_TYPE =
        "SELECT transaction_id, from_account, to_account, transaction_type, " +
        "       amount, transaction_time, remarks " +
        "FROM transactions " +
        "WHERE transaction_type = ? " +
        "ORDER BY transaction_time DESC";

    // ----------------------------------------------------------------
    // Insert (within an existing transaction)
    // ----------------------------------------------------------------

    /**
     * Inserts a transaction log entry using an existing connection.
     *
     * This method does NOT manage the connection lifecycle — it is
     * the caller's responsibility to commit or rollback.
     *
     * @param conn        the connection from the caller's transaction
     * @param transaction the transaction to log
     * @throws SQLException if the insert fails (triggers rollback in caller)
     */
    public void insert(Connection conn, Transaction transaction) throws SQLException {
        logger.debug("Logging transaction: type={}, amount={}",
            transaction.getTransactionType(), transaction.getAmount());

        try (PreparedStatement ps = conn.prepareStatement(
                SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // setString handles null values correctly for nullable columns
            // If fromAccount is null (DEPOSIT), setNull is used
            if (transaction.getFromAccount() != null) {
                ps.setString(1, transaction.getFromAccount());
            } else {
                ps.setNull(1, Types.VARCHAR);
            }

            if (transaction.getToAccount() != null) {
                ps.setString(2, transaction.getToAccount());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }

            ps.setString(3, transaction.getTransactionType().name());
            ps.setBigDecimal(4, transaction.getAmount());

            if (transaction.getRemarks() != null) {
                ps.setString(5, transaction.getRemarks());
            } else {
                ps.setNull(5, Types.VARCHAR);
            }

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Transaction log insert failed.");
            }

            // Retrieve and set the generated transaction ID
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    transaction.setTransactionId(keys.getLong(1));
                }
            }

            logger.debug("Transaction logged with ID: {}", transaction.getTransactionId());
        }
    }

    // ----------------------------------------------------------------
    // Query operations (use their own connections — read-only)
    // ----------------------------------------------------------------

    /**
     * Retrieves all transactions for a specific account number.
     * Searches both from_account and to_account columns.
     * Results are sorted newest-first.
     *
     * @param accountNumber the account number to search for
     * @return list of transactions involving this account
     */
    public List<Transaction> findByAccountNumber(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ACCOUNT)) {

            // The account number appears in both WHERE conditions
            ps.setString(1, accountNumber);
            ps.setString(2, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching transactions for account {}: {}",
                accountNumber, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        logger.debug("Found {} transactions for account {}", transactions.size(), accountNumber);
        return transactions;
    }

    /**
     * Retrieves the most recent transactions across all accounts.
     *
     * @param limit maximum number of transactions to return
     * @return list of recent transactions
     */
    public List<Transaction> findRecent(int limit) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching recent transactions: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return transactions;
    }

    /**
     * Retrieves all transactions of a specific type.
     *
     * @param type the transaction type to filter by
     * @return list of matching transactions
     */
    public List<Transaction> findByType(TransactionType type) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_TYPE)) {

            ps.setString(1, type.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching transactions by type {}: {}", type, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return transactions;
    }

    // ----------------------------------------------------------------
    // Private helper: ResultSet → Transaction mapping
    // ----------------------------------------------------------------

    private Transaction mapRow(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getLong("transaction_id"));
        t.setFromAccount(rs.getString("from_account"));   // may be null
        t.setToAccount(rs.getString("to_account"));       // may be null
        t.setTransactionType(
            TransactionType.valueOf(rs.getString("transaction_type"))
        );
        t.setAmount(rs.getBigDecimal("amount"));
        t.setRemarks(rs.getString("remarks"));

        Timestamp ts = rs.getTimestamp("transaction_time");
        if (ts != null) {
            t.setTransactionTime(ts.toLocalDateTime());
        }

        return t;
    }
}
