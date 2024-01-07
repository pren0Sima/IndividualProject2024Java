package org.transportCompanyProject.exceptions;
/**
 * A checked exception intended to be thrown when a Company object has an insufficient amount of money.
 * <p>
 *     Example: <i>ItineraryDao.executeItinerary(Itinerary itinerary)</i>
 *     The method throws this exception when company.balance < itinerary.cost.
 * </p>
 */
public class NotEnoughMoneyInCompanyException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotEnoughMoneyInCompanyException(String message) {
        super(message);
    }
}
