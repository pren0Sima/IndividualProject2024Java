package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.Driver;
import org.transportCompanyProject.models.entity.DrivingQualification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Data Access Object (DAO) class for managing Driver entities in the database.
 */
public class DriverDao {
    /**
     * Adds a new driver to the database.
     *
     * @param driver The Driver object to be added.
     */
    public static void addDriver(Driver driver){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(driver);
            transaction.commit();
        }
    }
    /**
     * Retrieves a driver by its id from the database.
     *
     * @param id The id of the Driver to be retrieved.
     * @return The Driver object with the specified id, or null if not found.
     */
    public static Driver getDriverById(long id) {
        Driver driver;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driver = session.get(Driver.class, id);
            transaction.commit();
        }
        return driver;
    }
    /**
     * Saves or updates an existing driver in the database.
     *
     * @param driver The Driver object to be saved or updated.
     */
    public static void saveOrUpdateDriver(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(driver);
            transaction.commit();
        }
    }
    /**
     * Deletes a driver from the database.
     *
     * @param driver The Driver object to be deleted.
     */
    public static void deleteDriver(Driver driver){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(driver);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all drivers from the database.
     *
     * @return List of all drivers in the database.
     */
    public static List<Driver> getDrivers() {
        List<Driver> drivers;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            drivers = session.createQuery("Select d From Driver d", Driver.class)
                    .getResultList();
            transaction.commit();
        }
        return drivers;
    }
    /**
     * Adds a driving qualification to a driver and saves the changes to the database.
     *
     * @param drivingQualification The DrivingQualification object to be added to the driver.
     * @param driver               The Driver object to which the qualification is added.
     */
    public static void addDrivingQualificationToDriver(DrivingQualification drivingQualification, Driver driver) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if(driver == null) {
                driver = new Driver();
            }
            if(driver.getDrivingQualifications() == null){
                Set<DrivingQualification> dqs = new HashSet<>();
                driver.setDrivingQualifications(dqs);
            }
            driver.getDrivingQualifications().add(drivingQualification);
            // if the qualification is not in the database => add it; same for the driver
            DrivingQualificationDao.saveOrUpdateDrivingQualification(drivingQualification);
            DriverDao.saveOrUpdateDriver(driver);

            transaction.commit();
        }
    }
}
