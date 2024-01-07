package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Data Access Object (DAO) class for managing Cargo entities in the database.
 */
public class CargoDao {
    /**
     * Adds a new cargo to the database.
     *
     * @param cargo The Cargo object to be added.
     */
    public static void addCargo(Cargo cargo) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(cargo);
            transaction.commit();
        }
    }
    /**
     * Retrieves a cargo by its id from the database.
     *
     * @param id The ID of the Cargo to be retrieved.
     * @return The Cargo object with the specified id, or null if it is not found.
     */
    public static Cargo getCargoById(long id) {
        Cargo client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(Cargo.class, id);
            transaction.commit();
        }
        return client;
    }
    /**
     * Saves or updates an existing Cargo object in the database.
     *
     * @param cargo The Cargo object to be saved or updated.
     */
    public static void saveOrUpdateCargo(Cargo cargo) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(cargo);
            transaction.commit();
        }
    }
    /**
     * Deletes a Cargo from the database.
     *
     * @param cargo The Cargo object to be deleted.
     */
    public static void deleteCargo(Cargo cargo){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(cargo);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all cargos, including both passengers and goods, from the database.
     *
     * @return List of all Cargos in the database.
     */
    public static List<Cargo> getFullCargoList(){
        List<Cargo> cargoList = new ArrayList<>();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Passenger> passengerList = session.createQuery("Select p From Passenger p", Passenger.class)
                    .getResultList();
            List<Goods> goodsList = session.createQuery("Select g From Goods g", Goods.class)
                    .getResultList();
            cargoList.addAll(passengerList);
            cargoList.addAll(goodsList);
            transaction.commit();
        }
        return cargoList;
    }
    /**
     * Retrieves the set of Cargos associated with a specific Itinerary from the database.
     *
     * @param id The id of the Itinerary.
     * @return Set of cargos associated with the specified Itinerary.
     */
    public static Set<Cargo> getItinerarysCargoList(long id){
        Itinerary itinerary;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itinerary = session.createQuery(
                            "select i from Itinerary i" +
                                    " join fetch i.cargo" +
                                    " where i.id = :id",
                            Itinerary.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();
        }
        return itinerary.getCargo();
    }
}
