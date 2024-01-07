package org.transportCompanyProject.exceptions;

/**
 * A custom checked exception intended to be thrown when a value is not positive when it is required.
 * <p>
 *     Example: In <i>ClientDao.addToBalance(BigDecimal amount, Client client)</i> the amount
 *     parameter should be a positive <i>BigDecimal</i> value, because it is not logical to add a negative
 *     or a <i>BigDecimal.ZERO</i> to a Client's balance.
 * </p>
 */
public class AmountShouldBePositiveException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AmountShouldBePositiveException(String message) {
        super(message);
    }
}
