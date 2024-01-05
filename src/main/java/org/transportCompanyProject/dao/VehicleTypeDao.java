package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.entity.VehicleType;

import java.util.List;

public class VehicleTypeDao {
    public static void addVehicleType(VehicleType vehicleType) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(vehicleType);
            transaction.commit();
        }
    }

    public static void deleteVehicleType(VehicleType vehicleType){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicleType);
            transaction.commit();
        }
    }
    public static VehicleType getVehicleTypeById(long id) {
        VehicleType vehicleType;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicleType = session.get(VehicleType.class, id);
            transaction.commit();
        }
        return vehicleType;
    }
    public static void saveOrUpdateVehicleType(VehicleType vehicleType) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(vehicleType);
            transaction.commit();
        }
    }
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
