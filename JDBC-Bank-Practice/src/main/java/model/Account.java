package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Account Model (Entity / POJO)
 *
 * Represents a row in the 'accounts' table.
 *
 * Design Note on BigDecimal:
 *   We use BigDecimal (not double/float) for monetary values.
 *   Floating-point types cannot represent all decimal fractions exactly
 *   (e.g., 0.1 + 0.2 != 0.3 in IEEE 754). BigDecimal provides exact
 *   decimal arithmetic, which is mandatory for financial applications.
 *
 * Design Note on AccountType:
 *   Using an enum instead of a raw String prevents invalid values
 *   from ever being constructed in Java code.
 */
public class Account {

    /**
     * Enum mirrors the MySQL ENUM('SAVINGS','CURRENT') column.
     * Provides compile-time safety — you cannot accidentally pass
     * an invalid account type string.
     */
    public enum AccountType {
        SAVINGS, CURRENT;

        /** Parse from string safely, defaulting to SAVINGS */
        public static AccountType fromString(String value) {
            try {
                return AccountType.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return SAVINGS;
            }
        }
    }

    // ----------------------------------------------------------------
    // Fields
    // ----------------------------------------------------------------
    private int           accountId;
    private int           customerId;
    private String        accountNumber;
    private AccountType   accountType;
    private BigDecimal    balance;
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------

    public Account() {}

    /**
     * Constructor for creating a new account (before DB insert).
     */
    public Account(int customerId, String accountNumber,
                   AccountType accountType, BigDecimal initialBalance) {
        this.customerId    = customerId;
        this.accountNumber = accountNumber;
        this.accountType   = accountType;
        this.balance       = initialBalance;
    }

    /**
     * Full constructor — used when reading from the database.
     */
    public Account(int accountId, int customerId, String accountNumber,
                   AccountType accountType, BigDecimal balance,
                   LocalDateTime createdAt) {
        this.accountId     = accountId;
        this.customerId    = customerId;
        this.accountNumber = accountNumber;
        this.accountType   = accountType;
        this.balance       = balance;
        this.createdAt     = createdAt;
    }

    // ----------------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------------

    public int getAccountId()                    { return accountId; }
    public void setAccountId(int id)             { this.accountId = id; }

    public int getCustomerId()                   { return customerId; }
    public void setCustomerId(int id)            { this.customerId = id; }

    public String getAccountNumber()             { return accountNumber; }
    public void setAccountNumber(String num)     { this.accountNumber = num; }

    public AccountType getAccountType()          { return accountType; }
    public void setAccountType(AccountType type) { this.accountType = type; }

    public BigDecimal getBalance()               { return balance; }
    public void setBalance(BigDecimal balance)   { this.balance = balance; }

    public LocalDateTime getCreatedAt()          { return createdAt; }
    public void setCreatedAt(LocalDateTime t)    { this.createdAt = t; }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return String.format(
            "Account{id=%d, customerId=%d, number='%s', type=%s, balance=%.2f, createdAt=%s}",
            accountId, customerId, accountNumber, accountType, balance, createdAt
        );
    }
}
