package model;

import java.time.LocalDateTime;

/**
 * Customer Model (Entity / POJO)
 *
 * Represents a row in the 'customers' table.
 * This is a plain Java object — it holds data only, no business logic.
 *
 * Design Note:
 *   In enterprise architecture, model classes map 1:1 to database tables.
 *   They are passed between layers (DAO → Service → UI) without exposing
 *   any database-specific details.
 */
public class Customer {

    // ----------------------------------------------------------------
    // Fields — mirror the database columns exactly
    // ----------------------------------------------------------------
    private int           customerId;
    private String        fullName;
    private String        email;
    private String        phone;
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------

    /** Default constructor — required for ResultSet mapping in DAO */
    public Customer() {}

    /**
     * Constructor for creating a new customer (before DB insert).
     * customerId and createdAt are assigned by the database.
     */
    public Customer(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email    = email;
        this.phone    = phone;
    }

    /**
     * Full constructor — used when reading a customer back from the database.
     */
    public Customer(int customerId, String fullName, String email,
                    String phone, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.fullName   = fullName;
        this.email      = email;
        this.phone      = phone;
        this.createdAt  = createdAt;
    }

    // ----------------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------------

    public int getCustomerId()               { return customerId; }
    public void setCustomerId(int id)        { this.customerId = id; }

    public String getFullName()              { return fullName; }
    public void setFullName(String name)     { this.fullName = name; }

    public String getEmail()                 { return email; }
    public void setEmail(String email)       { this.email = email; }

    public String getPhone()                 { return phone; }
    public void setPhone(String phone)       { this.phone = phone; }

    public LocalDateTime getCreatedAt()      { return createdAt; }
    public void setCreatedAt(LocalDateTime t){ this.createdAt = t; }

    // ----------------------------------------------------------------
    // toString — useful for logging and debugging
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return String.format(
            "Customer{id=%d, name='%s', email='%s', phone='%s', createdAt=%s}",
            customerId, fullName, email, phone, createdAt
        );
    }
}
