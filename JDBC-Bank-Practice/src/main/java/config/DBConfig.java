package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DBConfig — HikariCP DataSource Singleton
 *
 * ============================================================
 * WHY HikariCP?
 * ============================================================
 * Without a connection pool, every database operation would:
 *   1. Open a TCP socket to MySQL
 *   2. Perform TCP 3-way handshake
 *   3. Authenticate (username/password exchange)
 *   4. Execute the query
 *   5. Close the connection (4-way TCP teardown)
 *
 * Steps 1-3 and 5 are pure overhead — they can take 20-100ms
 * each. For a system doing 1000 requests/second, this is
 * catastrophic.
 *
 * HikariCP maintains a pool of pre-authenticated, pre-opened
 * connections. When your code calls getConnection(), HikariCP
 * hands you an existing connection from the pool in ~microseconds.
 * When you close() it, it returns to the pool — not actually closed.
 *
 * ============================================================
 * SINGLETON PATTERN
 * ============================================================
 * We use the "Initialization-on-demand holder" idiom for
 * thread-safe lazy initialization without synchronization overhead.
 * The HikariDataSource is created exactly once per JVM lifetime.
 *
 * ============================================================
 * CONNECTION LIFECYCLE
 * ============================================================
 *   1. Application starts → HikariCP creates minimumIdle connections
 *   2. Code calls DBConfig.getConnection() → borrows from pool
 *   3. Code uses connection (queries, transactions)
 *   4. Code calls connection.close() → returns to pool (NOT closed)
 *   5. Application shuts down → DBConfig.shutdown() closes all real connections
 */
public class DBConfig {

    private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

    // The HikariDataSource manages the entire connection pool.
    // It is thread-safe and designed to be shared across the application.
    private static HikariDataSource dataSource;

    // Private constructor — prevents instantiation.
    // This class is purely static (utility/singleton pattern).
    private DBConfig() {}

    /**
     * Initialization-on-demand holder.
     * The JVM guarantees that the static initializer of a class runs
     * exactly once, and only when the class is first accessed.
     * This gives us thread-safe lazy initialization for free.
     */
    private static class Holder {
        static {
            initialize();
        }
        // Holder class is loaded only when getInstance() is first called
        static final HikariDataSource INSTANCE = dataSource;
    }

    /**
     * Reads db.properties from the classpath and configures HikariCP.
     * Called exactly once by the Holder static initializer.
     */
    private static void initialize() {
        logger.info("Initializing HikariCP connection pool...");

        Properties props = loadProperties();

        // ----------------------------------------------------------------
        // HikariConfig — the configuration object for HikariCP
        // ----------------------------------------------------------------
        HikariConfig config = new HikariConfig();

        // JDBC URL: tells the driver which database server and database to connect to
        config.setJdbcUrl(props.getProperty("db.url"));

        // Database credentials
        config.setUsername(props.getProperty("db.username"));
        config.setPassword(props.getProperty("db.password"));

        // ----------------------------------------------------------------
        // Pool Sizing
        // ----------------------------------------------------------------
        // Maximum connections in the pool. When all are in use, new requests
        // wait up to connectionTimeout before throwing an exception.
        config.setMaximumPoolSize(
            Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "10"))
        );

        // Minimum idle connections HikariCP tries to maintain.
        // If idle connections drop below this, new ones are created.
        config.setMinimumIdle(
            Integer.parseInt(props.getProperty("hikari.minimumIdle", "3"))
        );

        // ----------------------------------------------------------------
        // Timeout Settings
        // ----------------------------------------------------------------
        // How long to wait for a connection from the pool (milliseconds)
        config.setConnectionTimeout(
            Long.parseLong(props.getProperty("hikari.connectionTimeout", "30000"))
        );

        // How long an idle connection can sit in the pool before being removed
        config.setIdleTimeout(
            Long.parseLong(props.getProperty("hikari.idleTimeout", "600000"))
        );

        // Maximum lifetime of a connection. Prevents using stale connections.
        // Must be less than MySQL's wait_timeout (default 8 hours).
        config.setMaxLifetime(
            Long.parseLong(props.getProperty("hikari.maxLifetime", "1800000"))
        );

        // ----------------------------------------------------------------
        // Connection Leak Detection
        // ----------------------------------------------------------------
        // If a connection is borrowed but not returned within this threshold,
        // HikariCP logs a warning with the stack trace of the borrower.
        // Invaluable for finding connection leaks in development.
        config.setLeakDetectionThreshold(
            Long.parseLong(props.getProperty("hikari.leakDetectionThreshold", "60000"))
        );

        // ----------------------------------------------------------------
        // Pool Name — appears in logs and JMX monitoring
        // ----------------------------------------------------------------
        config.setPoolName(props.getProperty("hikari.poolName", "BankingHikariPool"));

        // ----------------------------------------------------------------
        // Connection Validation
        // ----------------------------------------------------------------
        // SQL query used to test if a connection is still alive.
        // HikariCP runs this before handing a connection to your code.
        config.setConnectionTestQuery("SELECT 1");

        // ----------------------------------------------------------------
        // MySQL-specific optimizations
        // ----------------------------------------------------------------
        // Cache prepared statements on the server side for performance
        config.addDataSourceProperty("cachePrepStmts",          "true");
        config.addDataSourceProperty("prepStmtCacheSize",        "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit",    "2048");
        config.addDataSourceProperty("useServerPrepStmts",       "true");
        config.addDataSourceProperty("useLocalSessionState",     "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata",   "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits",      "true");
        config.addDataSourceProperty("maintainTimeStats",        "false");

        // Create the actual DataSource — this opens the initial connections
        dataSource = new HikariDataSource(config);

        logger.info("HikariCP pool '{}' initialized. Pool size: min={}, max={}",
            config.getPoolName(),
            config.getMinimumIdle(),
            config.getMaximumPoolSize()
        );
    }

    /**
     * Returns a Connection borrowed from the HikariCP pool.
     *
     * IMPORTANT: Always use this inside try-with-resources:
     *   try (Connection conn = DBConfig.getConnection()) { ... }
     *
     * When the try block exits, connection.close() is called automatically,
     * which returns the connection to the pool (not actually closes it).
     *
     * @return a live, pool-managed Connection
     * @throws SQLException if no connection is available within connectionTimeout
     */
    public static Connection getConnection() throws SQLException {
        return Holder.INSTANCE.getConnection();
    }

    /**
     * Gracefully shuts down the connection pool.
     * Closes all idle connections and waits for active ones to finish.
     * Must be called when the application exits.
     */
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            logger.info("Shutting down HikariCP connection pool...");
            dataSource.close();
            logger.info("HikariCP pool shut down successfully.");
        }
    }

    /**
     * Loads database configuration from db.properties on the classpath.
     */
    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = DBConfig.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (is == null) {
                throw new RuntimeException(
                    "db.properties not found on classpath. " +
                    "Ensure it exists in src/main/resources/"
                );
            }
            props.load(is);
            logger.debug("Loaded db.properties successfully.");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
        return props;
    }
}
