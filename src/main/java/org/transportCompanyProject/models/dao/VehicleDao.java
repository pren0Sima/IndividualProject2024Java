package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.VehicleDto;
import org.transportCompanyProject.models.entity.Vehicle;
import org.transportCompanyProject.exceptions.VehicleHasNoCompanyException;

import java.util.List;
/**
 * Data Access Object (DAO) class for performing database operations related to Vehicle entities.
 */
public class VehicleDao {
    /**
     * Adds a new Vehicle to the database.
     *
     * @param vehicle The Vehicle object to be added.
     */
    public static void addVehicle(Vehicle vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(vehicle);
            transaction.commit();
        }
    }
    /**
     * Retrieves a Vehicle by its id from the database.
     *
     * @param id The id of the Vehicle to be retrieved.
     * @return The Vehicle object with the specified id, or null if not found.
     */
    public static Vehicle getVehicleById(long id) {
        Vehicle vehicle;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicle = session.get(Vehicle.class, id);
            transaction.commit();
        }
        return vehicle;
    }
    /**
     * Saves or updates an existing Vehicle in the database.
     *
     * @param vehicle The Vehicle object to be saved or updated.
     */
    public static void saveOrUpdateVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(vehicle);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all Vehicles from the database.
     *
     * @return List of all Vehicles in the database.
     */
    public static List<Vehicle> getVehicles() {
        List<Vehicle> vehicles;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicles = session.createQuery("Select v From Vehicle v", Vehicle.class)
                    .getResultList();
            transaction.commit();
        }
        return vehicles;
    }
    /**
     * Retrieves a list of VehicleDto objects containing specific information about each Vehicle.
     *
     * @return List of VehicleDto objects.
     */
    public static List<VehicleDto> getVehiclesDTO() {
        List<VehicleDto> vehicles;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicles = session
                    .createQuery("select new org.transportCompanyProject.models.dto.VehicleDto(v.id, v.company, v.vehicleType) " +
                            "from Vehicle v", VehicleDto.class)
                    .getResultList();
            transaction.commit();
        }
        return vehicles;
    }
    /**
     * Deletes a Vehicle from the database.
     *
     * @param vehicle The Vehicle object to be deleted.
     */
    public static void deleteVehicle(Vehicle vehicle){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicle);
            transaction.commit();
        }
    }
    /**
     * Validates whether a Vehicle has an assigned company.
     *
     * @param vehicle The Vehicle to be validated.
     * @return True if the Vehicle has an assigned company, false otherwise.
     * @throws VehicleHasNoCompanyException If the Vehicle has no assigned company.
     */
    public static boolean validateVehicle(Vehicle vehicle) throws VehicleHasNoCompanyException {
        if (vehicle.getCompany() == null) throw new VehicleHasNoCompanyException("Assign a company to the vehicle!");
        else return true;
    }
}
