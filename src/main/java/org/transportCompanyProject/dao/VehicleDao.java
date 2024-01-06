package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.VehicleDto;

import java.util.List;

public class VehicleDao {
    public static void addVehicle(org.transportCompanyProject.entity.Vehicle vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(vehicle);
            transaction.commit();
        }
    }
    public static org.transportCompanyProject.entity.Vehicle getVehicleById(long id) {
        org.transportCompanyProject.entity.Vehicle company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(org.transportCompanyProject.entity.Vehicle.class, id);
            transaction.commit();
        }
        return company;
    }
    public static void saveOrUpdateVehicle(org.transportCompanyProject.entity.Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(vehicle);
            transaction.commit();
        }
    }
    public static List<org.transportCompanyProject.entity.Vehicle> getVehicles() {
        List<org.transportCompanyProject.entity.Vehicle> vehicles;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicles = session.createQuery("Select v From Vehicle v", org.transportCompanyProject.entity.Vehicle.class)
                    .getResultList();
            transaction.commit();
        }
        return vehicles;
    }
    public static List<VehicleDto> getVehiclesDTO() {
        List<VehicleDto> vehicles;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicles = session
                    .createQuery("select new org.transportCompanyProject.dto.VehicleDto(v.id, v.company, v.vehicleType) " +
                            "from Vehicle v", VehicleDto.class)
                    .getResultList();
            transaction.commit();
        }
        return vehicles;
    }
    public static void deleteVehicle(org.transportCompanyProject.entity.Vehicle vehicle){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicle);
            transaction.commit();
        }
    }
}
