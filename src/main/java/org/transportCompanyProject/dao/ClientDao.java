package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.ClientDto;
import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;
import org.transportCompanyProject.interfaces.Accounting;

import java.math.BigDecimal;
import java.util.List;

public class ClientDao implements Accounting<org.transportCompanyProject.entity.Client> {
    public static void addClient(org.transportCompanyProject.entity.Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(client);
            transaction.commit();
        }
    }
    public static org.transportCompanyProject.entity.Client getClientById(long id) {
        org.transportCompanyProject.entity.Client client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(org.transportCompanyProject.entity.Client.class, id);
            transaction.commit();
        }
        return client;
    }
    public static void saveOrUpdateClient(org.transportCompanyProject.entity.Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(client);
            transaction.commit();
        }
    }
    public static void deleteClient(org.transportCompanyProject.entity.Client client){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(client);
            transaction.commit();
        }
    }
    public static List<org.transportCompanyProject.entity.Client> getClients() {
        List<org.transportCompanyProject.entity.Client> clients;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session.createQuery("Select c From Client c", org.transportCompanyProject.entity.Client.class)
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

    @Override
    public void addToBalance(BigDecimal amount, org.transportCompanyProject.entity.Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().add(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
    @Override
    public void subtractFromBalance(BigDecimal amount, org.transportCompanyProject.entity.Client client) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        client.setBalance(client.getBalance().subtract(amount));
        // save the change into the db
        saveOrUpdateClient(client);
    }
}
