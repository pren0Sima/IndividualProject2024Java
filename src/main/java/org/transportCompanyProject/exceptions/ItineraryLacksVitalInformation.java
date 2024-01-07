package org.transportCompanyProject.exceptions;

/**
 * A checked exception intended to be thrown when an Itinerary object has not been created or has insufficient amount of data stored in it.
 * <p>
 *     Example: <i>ItineraryDao.validateItinerary(Itinerary itinerary)</i>
 *     It returns true is the itinerary is initialized and has a client and a vehicle,
 *     and throws this exception if these requirements are not satisfied.
 * </p>
 */
public class ItineraryLacksVitalInformation extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ItineraryLacksVitalInformation(String message) {
        super(message);
    }
}
