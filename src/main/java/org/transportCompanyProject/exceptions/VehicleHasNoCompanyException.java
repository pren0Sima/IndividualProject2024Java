package org.transportCompanyProject.exceptions;
/**
 * A checked exception intended to be thrown when a Vehicle object has no Company object assigned to it.
 * <p>
 *     It is used when operations with a vehicle's company field are to be done but no company is set.
 *     Example: <i>ItineraryDao.executeItinerary(Itinerary itinerary)</i>
 *     This method throws this exception when itinerary.vehicle.company == null.
 * </p>
 */
public class VehicleHasNoCompanyException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public VehicleHasNoCompanyException(String message) {
        super(message);
    }
}
