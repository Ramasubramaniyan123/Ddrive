package dao;

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
import exception.DuplicateEmailException;
import model.Customer;

/**
 * CustomerDAO — Data Access Object for the 'customers' table.
 *
 * ============================================================
 * DAO PATTERN EXPLAINED
 * ============================================================
 * The DAO pattern separates the data access logic from the
 * business logic. This class is the ONLY place in the application
 * that knows about the 'customers' table structure.
 *
 * Benefits:
 *   - If you switch from MySQL to PostgreSQL, only this class changes.
 *   - Service layer is clean — it works with Customer objects, not SQL.
 *   - Easy to unit test by mocking the DAO interface.
 *
 * ============================================================
 * WHY PreparedStatement (NEVER Statement)?
 * ============================================================
 * Statement:
 *   String sql = "SELECT * FROM customers WHERE email = '" + email + "'";
 *   If email = "' OR '1'='1", the query becomes:
 *   SELECT * FROM customers WHERE email = '' OR '1'='1'
 *   → Returns ALL customers! This is SQL Injection.
 *
 * PreparedStatement:
 *   String sql = "SELECT * FROM customers WHERE email = ?";
 *   ps.setString(1, email);
 *   The '?' is a parameter placeholder. The driver sends the SQL
 *   template and the parameter value SEPARATELY to the database.
 *   The database treats the parameter as pure data, never as SQL code.
 *   SQL Injection is structurally impossible.
 *
 * ============================================================
 * WHY try-with-resources?
 * ============================================================
 * Connection, PreparedStatement, and ResultSet all implement
 * AutoCloseable. try-with-resources guarantees close() is called
 * even if an exception is thrown, preventing resource leaks.
 * Without this, a thrown exception would leave connections open,
 * eventually exhausting the pool.
 */
public class CustomerDAO {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    // ----------------------------------------------------------------
    // SQL Constants
    // ----------------------------------------------------------------
    // Defining SQL as constants (not inline strings) makes them easy
    // to find, review, and modify. It also enables IDE syntax highlighting.

    private static final String SQL_INSERT =
        "INSERT INTO customers (full_name, email, phone) VALUES (?, ?, ?)";

    private static final String SQL_FIND_BY_ID =
        "SELECT customer_id, full_name, email, phone, created_at " +
        "FROM customers WHERE customer_id = ?";

    private static final String SQL_FIND_BY_EMAIL =
        "SELECT customer_id, full_name, email, phone, created_at " +
        "FROM customers WHERE email = ?";

    private static final String SQL_FIND_ALL =
        "SELECT customer_id, full_name, email, phone, created_at " +
        "FROM customers ORDER BY created_at DESC";

    private static final String SQL_UPDATE =
        "UPDATE customers SET full_name = ?, email = ?, phone = ? " +
        "WHERE customer_id = ?";

    private static final String SQL_DELETE =
        "DELETE FROM customers WHERE customer_id = ?";

    // ----------------------------------------------------------------
    // CRUD Operations
    // ----------------------------------------------------------------

    /**
     * Inserts a new customer into the database.
     *
     * Uses Statement.RETURN_GENERATED_KEYS to retrieve the
     * auto-incremented customer_id assigned by MySQL.
     *
     * @param customer the customer to insert (customerId will be set after insert)
     * @return the inserted Customer with customerId populated
     * @throws DuplicateEmailException if the email already exists
     */
    public Customer insert(Customer customer) {
        logger.debug("Inserting customer: {}", customer.getEmail());

        // try-with-resources: Connection and PreparedStatement are auto-closed
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // Bind parameters — this is what prevents SQL injection
            // The JDBC driver sends these as typed parameters, not SQL text
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insert failed — no rows affected.");
            }

            // Retrieve the auto-generated primary key
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerId(generatedKeys.getInt(1));
                    logger.info("Customer created with ID: {}", customer.getCustomerId());
                }
            }

            return customer;

        } catch (SQLException e) {
            // MySQL error code 1062 = Duplicate entry for UNIQUE constraint
            // SQLState 23000 = Integrity constraint violation
            if ("23000".equals(e.getSQLState()) || e.getErrorCode() == 1062) {
                logger.warn("Duplicate email attempt: {}", customer.getEmail());
                throw new DuplicateEmailException(customer.getEmail());
            }
            logger.error("Failed to insert customer: {}", e.getMessage());
            throw new RuntimeException("Failed to create customer: " + e.getMessage(), e);
        }
    }

    /**
     * Finds a customer by their primary key.
     *
     * Returns Optional.empty() instead of null when not found.
     * This forces the caller to handle the "not found" case explicitly,
     * preventing NullPointerExceptions.
     *
     * @param customerId the customer's primary key
     * @return Optional containing the Customer, or empty if not found
     */
    public Optional<Customer> findById(int customerId) {
        logger.debug("Finding customer by ID: {}", customerId);

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, customerId);

            // ResultSet is also AutoCloseable — nested try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding customer by ID {}: {}", customerId, e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    /**
     * Finds a customer by their email address.
     *
     * @param email the email to search for
     * @return Optional containing the Customer, or empty if not found
     */
    public Optional<Customer> findByEmail(String email) {
        logger.debug("Finding customer by email: {}", email);

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding customer by email: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    /**
     * Retrieves all customers, ordered by creation date (newest first).
     *
     * @return list of all customers (empty list if none exist)
     */
    public List<Customer> findAll() {
        logger.debug("Fetching all customers");
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Iterate through all rows in the ResultSet
            while (rs.next()) {
                customers.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error fetching all customers: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        logger.debug("Found {} customers", customers.size());
        return customers;
    }

    /**
     * Updates an existing customer's details.
     *
     * @param customer the customer with updated fields (customerId must be set)
     * @return true if the update succeeded, false if customer not found
     */
    public boolean update(Customer customer) {
        logger.debug("Updating customer ID: {}", customer.getCustomerId());

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setInt(4, customer.getCustomerId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                throw new DuplicateEmailException(customer.getEmail());
            }
            logger.error("Error updating customer: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a customer by ID.
     * Will fail if the customer has associated accounts (FK constraint).
     *
     * @param customerId the customer to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int customerId) {
        logger.debug("Deleting customer ID: {}", customerId);

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // FK constraint violation — customer has accounts
            if ("23000".equals(e.getSQLState())) {
                throw new RuntimeException(
                    "Cannot delete customer — they have active accounts. " +
                    "Delete all accounts first.", e
                );
            }
            logger.error("Error deleting customer: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------
    // Batch Insert (demonstrates JDBC batch processing)
    // ----------------------------------------------------------------

    /**
     * Inserts multiple customers in a single batch operation.
     *
     * ============================================================
     * BATCH PROCESSING EXPLAINED
     * ============================================================
     * Without batching: N customers = N round trips to the database
     *   Each round trip: ~1-5ms network latency + query execution
     *   1000 customers = ~1000-5000ms
     *
     * With batching: N customers = 1 round trip
     *   The JDBC driver accumulates all INSERT statements and sends
     *   them to MySQL in a single network packet (when
     *   rewriteBatchedStatements=true is set in the JDBC URL).
     *   1000 customers = ~5-20ms
     *
     * This is a 50-250x performance improvement for bulk operations.
     *
     * @param customers list of customers to insert
     * @return array of update counts (one per customer)
     */
    public int[] batchInsert(List<Customer> customers) {
        logger.info("Batch inserting {} customers", customers.size());

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // Disable auto-commit for the batch — all or nothing
            conn.setAutoCommit(false);

            try {
                for (Customer customer : customers) {
                    ps.setString(1, customer.getFullName());
                    ps.setString(2, customer.getEmail());
                    ps.setString(3, customer.getPhone());
                    // addBatch() queues this set of parameters
                    // No database round trip happens here
                    ps.addBatch();
                }

                // executeBatch() sends ALL queued statements to the database
                // in a single network operation
                int[] results = ps.executeBatch();
                conn.commit(); // Commit the entire batch atomically

                logger.info("Batch insert completed. {} customers inserted.", results.length);
                return results;

            } catch (SQLException e) {
                // If any insert fails, roll back ALL inserts in this batch
                conn.rollback();
                logger.error("Batch insert failed, rolled back: {}", e.getMessage());
                throw new RuntimeException("Batch insert failed: " + e.getMessage(), e);
            } finally {
                // Always restore auto-commit to true
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            logger.error("Failed to get connection for batch insert: {}", e.getMessage());
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------
    // Private helper: ResultSet → Customer mapping
    // ----------------------------------------------------------------

    /**
     * Maps the current row of a ResultSet to a Customer object.
     *
     * Centralizing this mapping prevents duplication across multiple
     * query methods. If the table schema changes, only this method
     * needs updating.
     *
     * @param rs a ResultSet positioned at a valid row
     * @return a populated Customer object
     * @throws SQLException if a column cannot be read
     */
    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFullName(rs.getString("full_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));

        // Convert java.sql.Timestamp → java.time.LocalDateTime
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            customer.setCreatedAt(ts.toLocalDateTime());
        }

        return customer;
    }
}
