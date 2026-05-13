package exception;

/**
 * DuplicateEmailException
 *
 * Thrown when attempting to create a customer with an email
 * address that already exists in the database.
 *
 * This is detected by catching the MySQL duplicate key error
 * (SQLState 23000, vendor code 1062) in the DAO layer and
 * re-throwing as this domain-specific exception.
 */
public class DuplicateEmailException extends BankingException {

    private final String email;

    public DuplicateEmailException(String email) {
        super("DUPLICATE_EMAIL",
              "A customer with email '" + email + "' already exists.");
        this.email = email;
    }

    public String getEmail() { return email; }
}
