package exception;

import java.math.BigDecimal;

/**
 * InsufficientFundsException
 *
 * Thrown when a withdrawal or transfer is attempted but the
 * account balance is lower than the requested amount.
 *
 * Carrying the balance and requested amount in the exception
 * allows the UI layer to display a precise, helpful error message
 * without needing to re-query the database.
 */
public class InsufficientFundsException extends BankingException {

    private final BigDecimal availableBalance;
    private final BigDecimal requestedAmount;

    public InsufficientFundsException(BigDecimal availableBalance,
                                      BigDecimal requestedAmount) {
        super("INSUFFICIENT_FUNDS",
              String.format("Insufficient funds. Available: %.2f, Requested: %.2f",
                            availableBalance, requestedAmount));
        this.availableBalance = availableBalance;
        this.requestedAmount  = requestedAmount;
    }

    public BigDecimal getAvailableBalance() { return availableBalance; }
    public BigDecimal getRequestedAmount()  { return requestedAmount; }
}
