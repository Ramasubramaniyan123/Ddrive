package exception;

/**
 * BankingException — Base Custom Exception
 *
 * All application-specific exceptions extend this class.
 * This allows callers to catch all banking-related errors
 * with a single catch block when needed:
 *
 *   catch (BankingException e) { ... }
 *
 * We extend RuntimeException (unchecked) so that DAO and Service
 * methods don't need to declare 'throws BankingException' everywhere,
 * keeping method signatures clean. The caller can still catch it
 * explicitly when needed.
 */
public class BankingException extends RuntimeException {

    private final String errorCode;

    public BankingException(String message) {
        super(message);
        this.errorCode = "BANKING_ERROR";
    }

    public BankingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BankingException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BANKING_ERROR";
    }

    public BankingException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
