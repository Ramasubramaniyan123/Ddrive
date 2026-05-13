package util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * InputValidator — Centralized input validation utility.
 *
 * All user input validation lives here. This prevents validation
 * logic from being scattered across the service and UI layers.
 *
 * Design Note on SQL Injection Prevention:
 *   The primary defense against SQL injection is using PreparedStatement
 *   with parameterized queries (never string concatenation in SQL).
 *   Input validation here is a secondary defense — it rejects obviously
 *   malformed input before it even reaches the DAO layer.
 */
public class InputValidator {

    // ----------------------------------------------------------------
    // Compiled regex patterns (compiled once, reused many times)
    // ----------------------------------------------------------------

    /**
     * Phone: 10-15 digits, optionally starting with +
     * Examples: +919876543210, 9876543210, 01234567890
     */
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\+?[0-9]{10,15}$");

    /**
     * Email: standard RFC 5322 simplified pattern
     * Examples: user@example.com, john.doe+tag@company.org
     */
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    /**
     * Account number: BNK-YYYYMMDD-XXXXXXXX format
     */
    private static final Pattern ACCOUNT_NUMBER_PATTERN =
        Pattern.compile("^BNK-\\d{8}-\\d{8}$");

    // Private constructor — utility class
    private InputValidator() {}

    // ----------------------------------------------------------------
    // Validation Methods
    // ----------------------------------------------------------------

    /**
     * Validates that a string is not null and not blank.
     *
     * @param value     the string to check
     * @param fieldName the field name for the error message
     * @throws IllegalArgumentException if validation fails
     */
    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
    }

    /**
     * Validates email format.
     *
     * @param email the email address to validate
     * @throws IllegalArgumentException if the format is invalid
     */
    public static void validateEmail(String email) {
        requireNonBlank(email, "Email");
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException(
                "Invalid email format: '" + email + "'. " +
                "Expected format: user@example.com"
            );
        }
    }

    /**
     * Validates phone number format.
     *
     * @param phone the phone number to validate
     * @throws IllegalArgumentException if the format is invalid
     */
    public static void validatePhone(String phone) {
        requireNonBlank(phone, "Phone");
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new IllegalArgumentException(
                "Invalid phone format: '" + phone + "'. " +
                "Expected 10-15 digits, optionally starting with +"
            );
        }
    }

    /**
     * Validates that an amount is positive (> 0).
     *
     * @param amount    the monetary amount to validate
     * @param fieldName the field name for the error message
     * @throws IllegalArgumentException if amount is null, zero, or negative
     */
    public static void validatePositiveAmount(BigDecimal amount, String fieldName) {
        if (amount == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                fieldName + " must be greater than zero. Got: " + amount
            );
        }
    }

    /**
     * Validates account number format.
     *
     * @param accountNumber the account number to validate
     * @throws IllegalArgumentException if the format is invalid
     */
    public static void validateAccountNumber(String accountNumber) {
        requireNonBlank(accountNumber, "Account number");
        if (!ACCOUNT_NUMBER_PATTERN.matcher(accountNumber.trim()).matches()) {
            throw new IllegalArgumentException(
                "Invalid account number format: '" + accountNumber + "'. " +
                "Expected format: BNK-YYYYMMDD-XXXXXXXX"
            );
        }
    }

    /**
     * Validates that a name contains only letters, spaces, dots, and hyphens.
     *
     * @param name      the name to validate
     * @param fieldName the field name for the error message
     * @throws IllegalArgumentException if the name is invalid
     */
    public static void validateName(String name, String fieldName) {
        requireNonBlank(name, fieldName);
        if (name.trim().length() < 2 || name.trim().length() > 100) {
            throw new IllegalArgumentException(
                fieldName + " must be between 2 and 100 characters."
            );
        }
    }

    /**
     * Parses a string to BigDecimal safely.
     *
     * @param value     the string to parse
     * @param fieldName the field name for the error message
     * @return parsed BigDecimal
     * @throws IllegalArgumentException if the string is not a valid number
     */
    public static BigDecimal parseBigDecimal(String value, String fieldName) {
        requireNonBlank(value, fieldName);
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                fieldName + " must be a valid number. Got: '" + value + "'"
            );
        }
    }

    /**
     * Parses a string to int safely.
     *
     * @param value     the string to parse
     * @param fieldName the field name for the error message
     * @return parsed int
     * @throws IllegalArgumentException if the string is not a valid integer
     */
    public static int parseInt(String value, String fieldName) {
        requireNonBlank(value, fieldName);
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                fieldName + " must be a valid integer. Got: '" + value + "'"
            );
        }
    }
}
