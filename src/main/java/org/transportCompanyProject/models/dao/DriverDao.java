package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.Driver;
import org.transportCompanyProject.models.entity.DrivingQualification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverDao {
    public static void addDriver(Driver driver){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(driver);
            transaction.commit();
        }
    }
    public static Driver getDriverById(long id) {
        Driver driver;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driver = session.get(Driver.class, id);
            transaction.commit();
        }
        return driver;
    }
    public static void saveOrUpdateDriver(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(driver);
            transaction.commit();
        }
    }
    public static void deleteDriver(Driver driver){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(driver);
            transaction.commit();
        }
    }
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
    // adding driving qualifications to drivers
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
