package exception;

/**
 * AccountNotFoundException
 *
 * Thrown when an operation references an account number that
 * does not exist in the database.
 */
public class AccountNotFoundException extends BankingException {

    private final String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        super("ACCOUNT_NOT_FOUND",
              "Account not found: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() { return accountNumber; }
}
