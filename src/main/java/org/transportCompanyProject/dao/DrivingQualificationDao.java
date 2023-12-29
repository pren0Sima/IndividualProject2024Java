package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.DrivingQualification;

import java.util.List;

public class DrivingQualificationDao {
    public static void addDrivingQualification(DrivingQualification drivingQualification){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(drivingQualification);
            transaction.commit();
        }
    }
    public static DrivingQualification getDrivingQualificationById(long id) {
        DrivingQualification company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(DrivingQualification.class, id);
            transaction.commit();
        }
        return company;
    }
    public static void saveOrUpdateDrivingQualification(DrivingQualification drivingQualification) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(drivingQualification);
            transaction.commit();
        }
    }
    public static void deleteDrivingQualification(DrivingQualification drivingQualification){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(drivingQualification);
            transaction.commit();
        }
    }
    public static List<DrivingQualification> getDrivingQualification() {
        List<DrivingQualification> drivingQualifications;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            drivingQualifications = session.createQuery("Select dq From DrivingQualification dq", DrivingQualification.class)
                    .getResultList();
            transaction.commit();
        }
        return drivingQualifications;
    }
}
