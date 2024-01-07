package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.VehicleType;

import java.util.List;
/**
 * Data Access Object (DAO) class for performing database operations related to VehicleType entities.
 */
public class VehicleTypeDao {
    /**
     * Adds a new VehicleType to the database.
     *
     * @param vehicleType The VehicleType object to be added.
     */
    public static void addVehicleType(VehicleType vehicleType) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(vehicleType);
            transaction.commit();
        }
    }
    /**
     * Deletes a VehicleType from the database.
     *
     * @param vehicleType The VehicleType object to be deleted.
     */
    public static void deleteVehicleType(VehicleType vehicleType){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicleType);
            transaction.commit();
        }
    }
    /**
     * Retrieves a VehicleType by its id from the database.
     *
     * @param id The id of the VehicleType to be retrieved.
     * @return The VehicleType object with the specified id, or null if not found.
     */
    public static VehicleType getVehicleTypeById(long id) {
        VehicleType vehicleType;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicleType = session.get(VehicleType.class, id);
            transaction.commit();
        }
        return vehicleType;
    }
    /**
     * Saves or updates an existing VehicleType in the database.
     *
     * @param vehicleType The VehicleType object to be saved or updated.
     */
    public static void saveOrUpdateVehicleType(VehicleType vehicleType) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(vehicleType);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all VehicleTypes from the database.
     *
     * @return List of all VehicleTypes in the database.
     */
    public static List<VehicleType> getVehicleTypes() {
        List<VehicleType> vehicleType;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicleType = session.createQuery("Select vt From VehicleType vt", VehicleType.class)
                    .getResultList();
            transaction.commit();
        }
        return vehicleType;
    }
}
