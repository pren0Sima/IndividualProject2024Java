package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.ClientDto;
import org.transportCompanyProject.entity.Client;
import org.transportCompanyProject.entity.Vehicle;
import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;
import org.transportCompanyProject.exceptions.ClientDoesNotExistException;
import org.transportCompanyProject.exceptions.VehicleHasNoCompanyException;
import org.transportCompanyProject.interfaces.Accounting;

import java.math.BigDecimal;
import java.util.List;

public class ClientDao implements Accounting<Client> {
    public static void addClient(Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(client);
            transaction.commit();
        }
    }
    public static Client getClientById(long id) {
        Client client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(Client.class, id);
            transaction.commit();
        }
        return client;
    }
    public static void saveOrUpdateClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(client);
            transaction.commit();
        }
    }
    public static void deleteClient(Client client){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(client);
            transaction.commit();
        }
    }
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
    public static List<ClientDto> getClientsDTO() {
        List<ClientDto> clients;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session
                    .createQuery("select new org.transportCompanyProject.dto.ClientDto(c.id, c.name) " +
                            "from Client c", ClientDto.class)
                    .getResultList();
            transaction.commit();
        }
        return clients;
    }
    public static boolean validateClient(Client client) throws ClientDoesNotExistException {
        if (client == null) throw new ClientDoesNotExistException("Client is null!");
        else return true;
    }
    public static boolean canAClientPay(BigDecimal price, Client client) {
        if (client.getBalance().compareTo(price) < 0)
            return false;
        else
            return true;
    }

    @Override
    public void addToBalance(BigDecimal amount, Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().add(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
    @Override
    public void subtractFromBalance(BigDecimal amount, Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().subtract(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
}
