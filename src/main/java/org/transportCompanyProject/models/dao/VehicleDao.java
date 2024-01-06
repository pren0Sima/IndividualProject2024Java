package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.VehicleDto;
import org.transportCompanyProject.models.entity.Vehicle;
import org.transportCompanyProject.exceptions.VehicleHasNoCompanyException;

import java.util.List;

public class VehicleDao {
    public static void addVehicle(Vehicle vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(vehicle);
            transaction.commit();
        }
    }
    public static Vehicle getVehicleById(long id) {
        Vehicle vehicle;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicle = session.get(Vehicle.class, id);
            transaction.commit();
        }
        return vehicle;
    }
    public static void saveOrUpdateVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(vehicle);
            transaction.commit();
        }
    }
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
    public static void deleteVehicle(Vehicle vehicle){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicle);
            transaction.commit();
        }
    }

    public static boolean validateVehicle(Vehicle vehicle) throws VehicleHasNoCompanyException {
        if (vehicle.getCompany() == null) throw new VehicleHasNoCompanyException("Assign a company to the vehicle!");
        else return true;
    }
}
