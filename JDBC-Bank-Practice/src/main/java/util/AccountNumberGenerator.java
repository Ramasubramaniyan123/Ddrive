package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AccountNumberGenerator — Utility for generating unique account numbers.
 *
 * Format: BNK-YYYYMMDD-XXXXXXXX
 *   - BNK     : Bank prefix (identifies our institution)
 *   - YYYYMMDD: Date component (provides rough chronological ordering)
 *   - XXXXXXXX: 8-digit random number (provides uniqueness within a day)
 *
 * Example: BNK-20240115-47382910
 *
 * Design Note:
 *   In a real banking system, account numbers follow strict formats
 *   defined by the central bank (e.g., IBAN in Europe). The uniqueness
 *   guarantee would come from a database sequence, not random generation.
 *   Here we use randomness + date for simplicity, with the database's
 *   UNIQUE constraint as the final safety net.
 *
 *   If a collision occurs (extremely rare), the DAO layer will catch
 *   the duplicate key exception and the service can retry generation.
 */
public class AccountNumberGenerator {

    private static final String PREFIX = "BNK";
    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyyMMdd");

    // Private constructor — this is a pure utility class, not instantiable
    private AccountNumberGenerator() {}

    /**
     * Generates a unique account number.
     *
     * @return account number string in format BNK-YYYYMMDD-XXXXXXXX
     */
    public static String generate() {
        String datePart   = LocalDate.now().format(DATE_FORMAT);
        // ThreadLocalRandom is faster than Random in multi-threaded environments
        int    randomPart = ThreadLocalRandom.current().nextInt(10_000_000, 99_999_999);

        return String.format("%s-%s-%d", PREFIX, datePart, randomPart);
    }
}
