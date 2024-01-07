package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.DrivingQualification;

import java.util.List;
/**
 * Data Access Object (DAO) class for managing DrivingQualification entities in the database.
 */
public class DrivingQualificationDao {
    /**
     * Adds a new driving qualification to the database.
     *
     * @param drivingQualification The DrivingQualification object to be added.
     */
    public static void addDrivingQualification(DrivingQualification drivingQualification){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(drivingQualification);
            transaction.commit();
        }
    }
    /**
     * Retrieves a driving qualification by its id from the database.
     *
     * @param id The id of the DrivingQualification to be retrieved.
     * @return The DrivingQualification object with the specified id, or null if not found.
     */
    public static DrivingQualification getDrivingQualificationById(long id) {
        DrivingQualification company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(DrivingQualification.class, id);
            transaction.commit();
        }
        return company;
    }
    /**
     * Saves or updates an existing driving qualification in the database.
     *
     * @param drivingQualification The DrivingQualification object to be saved or updated.
     */
    public static void saveOrUpdateDrivingQualification(DrivingQualification drivingQualification) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(drivingQualification);
            transaction.commit();
        }
    }
    /**
     * Deletes a driving qualification from the database.
     *
     * @param drivingQualification The DrivingQualification object to be deleted.
     */
    public static void deleteDrivingQualification(DrivingQualification drivingQualification){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(drivingQualification);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all driving qualifications from the database.
     *
     * @return List of all driving qualifications in the database.
     */
    public static List<DrivingQualification> getDrivingQualification() {
        List<DrivingQualification> drivingQualifications;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            drivingQualifications = session
                    .createQuery("Select dq From DrivingQualification dq", DrivingQualification.class)
                    .getResultList();
            transaction.commit();
        }
        return drivingQualifications;
    }
}
