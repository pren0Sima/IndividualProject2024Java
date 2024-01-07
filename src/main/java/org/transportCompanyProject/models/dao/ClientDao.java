package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.ClientDto;
import org.transportCompanyProject.models.entity.Client;
import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;
import org.transportCompanyProject.exceptions.ClientDoesNotExistException;

import java.math.BigDecimal;
import java.util.List;
/**
 * Data Access Object (DAO) class for managing Client entities in the database.
 */
public class ClientDao {
    /**
     * Adds a new client to the database.
     *
     * @param client The Client object to be added.
     */
    public static void addClient(Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(client);
            transaction.commit();
        }
    }
    /**
     * Retrieves a client by their id from the database.
     *
     * @param id The id of the Client to be retrieved.
     * @return The Client object with the specified id, or null if not found.
     */
    public static Client getClientById(long id) {
        Client client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(Client.class, id);
            transaction.commit();
        }
        return client;
    }
    /**
     * Saves or updates an existing client in the database.
     *
     * @param client The Client object to be saved or updated.
     */
    public static void saveOrUpdateClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(client);
            transaction.commit();
        }
    }
    /**
     * Deletes a client from the database.
     *
     * @param client The Client object to be deleted.
     */
    public static void deleteClient(Client client){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(client);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all clients from the database.
     *
     * @return List of all clients in the database.
     */
    public static List<Client> getClients() {
        List<Client> clients;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session.createQuery("Select c From Client c", Client.class)
                    .getResultList();
            transaction.commit();
        }
        return clients;
    }
    /**
     * Retrieves a list of ClientDto objects containing specific information about clients.
     *
     * @return List of ClientDto objects.
     */
    public static List<ClientDto> getClientsDTO() {
        List<ClientDto> clients;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session
                    .createQuery("select new org.transportCompanyProject.models.dto.ClientDto(c.id, c.name) " +
                            "from Client c", ClientDto.class)
                    .getResultList();
            transaction.commit();
        }
        return clients;
    }
    /**
     * Validates that a client object is not null.
     *
     * @param client The Client object to be validated.
     * @return true if the client is not null, otherwise throws ClientDoesNotExistException.
     * @throws ClientDoesNotExistException If the client is null.
     */
    public static boolean validateClient(Client client) throws ClientDoesNotExistException {
        if (client == null) throw new ClientDoesNotExistException("Client is null!");
        else return true;
    }
    /**
     * Checks if a client can pay a specified price based on their balance.
     *
     * @param price  The price to be checked against the client's balance.
     * @param client The Client object whose balance is checked.
     * @return true if the client's balance is greater than or equal to the specified price.
     */
    public static boolean canAClientPay(BigDecimal price, Client client) {
        return client.getBalance().compareTo(price) >= 0;
    }
    /**
     * Adds a specified amount to a client's balance.
     *
     * @param amount The amount to be added to the client's balance.
     * @param client The Client object whose balance is updated.
     * @throws AmountShouldBePositiveException If the specified amount is not a positive BigDecimal value.
     */
    public static void addToBalance(BigDecimal amount, Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().add(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
    /**
     * Subtracts a specified amount from a client's balance.
     *
     * @param amount The amount to be subtracted from the client's balance.
     * @param client The Client object whose balance is updated.
     * @throws AmountShouldBePositiveException If the specified amount is not a positive BigDecimal value.
     */
    public static void subtractFromBalance(BigDecimal amount, Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().subtract(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
}
