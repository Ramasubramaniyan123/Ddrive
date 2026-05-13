package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Model (Entity / POJO)
 *
 * Represents a row in the 'transactions' table.
 *
 * Design Note — Immutability:
 *   Transactions are financial records and should be treated as
 *   immutable once created. In a stricter design, you would make
 *   all fields final and remove setters. We keep setters here for
 *   ResultSet mapping convenience in the DAO layer.
 *
 * Design Note — Nullable accounts:
 *   - fromAccount is NULL for DEPOSIT (money enters from outside)
 *   - toAccount is NULL for WITHDRAWAL (money leaves the system)
 *   - Both are set for TRANSFER
 */
public class Transaction {

    /**
     * Enum mirrors the MySQL ENUM('DEPOSIT','WITHDRAWAL','TRANSFER') column.
     */
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    // ----------------------------------------------------------------
    // Fields
    // ----------------------------------------------------------------
    private long            transactionId;
    private String          fromAccount;    // nullable
    private String          toAccount;      // nullable
    private TransactionType transactionType;
    private BigDecimal      amount;
    private LocalDateTime   transactionTime;
    private String          remarks;

    // ----------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------

    public Transaction() {}

    /**
     * Constructor for creating a new transaction record (before DB insert).
     */
    public Transaction(String fromAccount, String toAccount,
                       TransactionType transactionType,
                       BigDecimal amount, String remarks) {
        this.fromAccount     = fromAccount;
        this.toAccount       = toAccount;
        this.transactionType = transactionType;
        this.amount          = amount;
        this.remarks         = remarks;
    }

    /**
     * Full constructor — used when reading from the database.
     */
    public Transaction(long transactionId, String fromAccount, String toAccount,
                       TransactionType transactionType, BigDecimal amount,
                       LocalDateTime transactionTime, String remarks) {
        this.transactionId   = transactionId;
        this.fromAccount     = fromAccount;
        this.toAccount       = toAccount;
        this.transactionType = transactionType;
        this.amount          = amount;
        this.transactionTime = transactionTime;
        this.remarks         = remarks;
    }

    // ----------------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------------

    public long getTransactionId()                   { return transactionId; }
    public void setTransactionId(long id)            { this.transactionId = id; }

    public String getFromAccount()                   { return fromAccount; }
    public void setFromAccount(String acc)           { this.fromAccount = acc; }

    public String getToAccount()                     { return toAccount; }
    public void setToAccount(String acc)             { this.toAccount = acc; }

    public TransactionType getTransactionType()      { return transactionType; }
    public void setTransactionType(TransactionType t){ this.transactionType = t; }

    public BigDecimal getAmount()                    { return amount; }
    public void setAmount(BigDecimal amount)         { this.amount = amount; }

    public LocalDateTime getTransactionTime()        { return transactionTime; }
    public void setTransactionTime(LocalDateTime t)  { this.transactionTime = t; }

    public String getRemarks()                       { return remarks; }
    public void setRemarks(String remarks)           { this.remarks = remarks; }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return String.format(
            "Transaction{id=%d, type=%s, from='%s', to='%s', amount=%.2f, time=%s}",
            transactionId, transactionType, fromAccount, toAccount, amount, transactionTime
        );
    }
}
