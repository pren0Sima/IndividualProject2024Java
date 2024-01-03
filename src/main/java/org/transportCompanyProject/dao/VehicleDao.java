package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.EmployeeDto;
import org.transportCompanyProject.dto.VehicleTypeDto;
import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.Employee;
import org.transportCompanyProject.entity.Vehicle;
import org.transportCompanyProject.entity.VehicleType;

import java.util.List;
import java.util.Set;

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
        Vehicle company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Vehicle.class, id);
            transaction.commit();
        }
        return company;
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
    public static void deleteVehicle(Vehicle vehicle){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(vehicle);
            transaction.commit();
        }
    }

    // make methods to extract the vehicle's type and company
//    public static VehicleTypeDto getVehiclesTypeDTO(long vehicle_id) {
//        VehicleTypeDto vehicleTypeDto;
//        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
//            Transaction transaction = session.beginTransaction();
//            vehicleTypeDto = session.createQuery(
//                            "select new org.transportCompanyProject.dto.VehicleTypeDto(v.id, v.type) from VehicleType v" +
//                                    " join v.company c" +
//                                    " where c.id = :id",
//                            EmployeeDto.class)
//                    .setParameter("id", vehicle_id)
//                    .getResultList();
//            transaction.commit();
//        }
//        return vehicleTypeDto;
//    }
}
