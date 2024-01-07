package org.transportCompanyProject.exceptions;

/**
 * A checked exception intended to be thrown when a Client object has not been created.
 * <p>
 *     It is used when operations with a client's data are to be done but there is no client.
 *     Example: <i>ClientDao.validateClient(Client client)</i>
 *     It returns true is the client is initialized and throws this exception when it is not.
 * </p>
 */
public class ClientDoesNotExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ClientDoesNotExistException(String message) {
        super(message);
    }
}
