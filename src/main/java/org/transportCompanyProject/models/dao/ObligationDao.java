package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.Obligation;

import java.util.List;
/**
 * Data Access Object (DAO) class for performing database operations related to Obligation entities.
 */
public class ObligationDao {
    /**
     * Adds a new Obligation to the database.
     *
     * @param obligation The Obligation object to be added.
     */
    public static void addObligation(Obligation obligation) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(obligation);
            transaction.commit();
        }
    }
    /**
     * Deletes an Obligation from the database.
     *
     * @param obligation The Obligation object to be deleted.
     */
    public static void deleteObligation(Obligation obligation){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(obligation);
            transaction.commit();
        }
    }
    /**
     * Retrieves an Obligation by its id from the database.
     *
     * @param id The id of the Obligation to be retrieved.
     * @return The Obligation object with the specified id, or null if not found.
     */
    public static Obligation getObligationById(long id) {
        Obligation obligation;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            obligation = session.get(Obligation.class, id);
            transaction.commit();
        }
        return obligation;
    }
    /**
     * Saves or updates an existing Obligation in the database.
     *
     * @param obligation The Obligation object to be saved or updated.
     */
    public static void saveOrUpdateObligation(Obligation obligation) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(obligation);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all Obligations from the database.
     *
     * @return List of all Obligations in the database.
     */
    public static List<Obligation> getObligations() {
        List<Obligation> obligations;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            obligations = session.createQuery("Select o From Obligation o", Obligation.class)
                    .getResultList();
            transaction.commit();
        }
        return obligations;
    }
}
