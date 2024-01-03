package org.transportCompanyProject;

import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dao.*;
import org.transportCompanyProject.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // I. Company.
        // 1. creating the database with all tables(without rows)
        SessionFactoryUtil.getSessionFactory().openSession();
//        // 2. adding a company in the company table:
        Company company1 = new Company(1, "Greco");
        company1.setOvercharge(BigDecimal.valueOf(20));
        Company company2 = new Company(2,"Orbico");
        company2.setOvercharge(BigDecimal.valueOf(25));
        Company company3 = new Company(3, "Telenor");
        company3.setOvercharge(BigDecimal.valueOf(30));
        Company company4 = new Company(4,"Union Ivkoni");
        company4.setOvercharge(BigDecimal.valueOf(20));
        CompanyDao.saveOrUpdateCompany(company1);
        CompanyDao.saveOrUpdateCompany(company2);
        CompanyDao.saveOrUpdateCompany(company3);
        CompanyDao.saveOrUpdateCompany(company4);
//        // 3. displaying a company from the db
//        System.out.println(CompanyDao.getCompanyById(2));
//        // 4. updating a company
//        // if we don't set it, it automatically increments the id => creates a new object
//        Company company = new Company();
//        company.setId(2);
//        company.setName("Vivakom");
//        CompanyDao.updateCompany(company);
//        // 5. get all companies and display them
        // first way:
//        CompanyDao.getCompanies().stream().forEach(System.out::println);
        // dto way
//        CompanyDao.getCompaniesDTO().stream().forEach(System.out::println);
//        // 6. delete a company
//        CompanyDao.deleteCompany(company);
//        // 7. count number of companies:
//        System.out.println("Total number of companies: " + CompanyDao.countCompanies());
        // 8. trying to add an invalid company - everything works fine here
//        Company invCompany = new Company("toomuchcharactersinthiscompanynameidkwheretostartidkwheretostartmustchangeitlatersperhapsorperhapsnot");
//        Company invCompany2 = new Company("nirvana");
//        CompanyDao.addCompany(invCompany);
//        CompanyDao.addCompany(invCompany2);

        // II. Employee
        // 1. Adding an employee to the base and connection it to a company
//        Employee employee = new Employee("Pavlina Velichkova", PositionType.MANAGER);
//        Company company5 = new Company("Orbico");
//        employee.setCompany(company5);
//        CompanyDao.addCompany(company5);
//        EmployeeDao.addEmployee(employee);
        // 2. deleting an employee
//        Employee employee1 = new Employee("Pavlina Velichkova", PositionType.MANAGER);
//        employee1.setCompany(CompanyDao.getCompanyById(6));
//        EmployeeDao.deleteEmployee(employee1);
        // 3. adding it back
//        EmployeeDao.addEmployee(employee1);
        // 4. adding some more employees
//        Employee employee2 = new Employee("Kiril Simeonov Velichkov", PositionType.ACCOUNTANT);
//        employee2.setCompany(CompanyDao.getCompanyById(6));
//        EmployeeDao.addEmployee(employee2);

        // 4. getting the employees of a company
//        // first way (with join fetch):
//         CompanyDao.getCompanyEmployees(6)
//                 .stream().forEach(System.out::println);
//        // second way (with dto):
////        System.out.println(CompanyDao.getCompanyEmployeesDTO(6));



        // Let's try again.
        // let's add employees. Again.
        Employee employee3 = new Employee(1, "Kiril Simeonov Velichkov", PositionType.ADMINISTRATOR, BigDecimal.valueOf(2000));
        employee3.setCompany(CompanyDao.getCompanyById(1));
        Employee employee4 = new Employee(2, "Pavlina Velichkova", PositionType.MANAGER, BigDecimal.valueOf(2000));
        employee4.setCompany(CompanyDao.getCompanyById(2));
        EmployeeDao.saveOrUpdateEmployee(employee3);
        EmployeeDao.saveOrUpdateEmployee(employee4);
        // works as long as there isn't a foreign key.!!!!!!!!!!!!!!!!!!!!!!!!! to be fixed in the future !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        EmployeeDao.getEmployeesDTO().stream().forEach(System.out::println);
        //        System.out.println("Company 1's employees: ");
//        CompanyDao.getCompanyEmployeesDTO(1)
//                .stream().forEach(System.out::println);

        // III. Client
        // 1. add clients
        Client client1 = new Client(1, "Samantha");
        Client client2 = new Client(2,"Lidl");
        ClientDao.saveOrUpdateClient(client1);
        ClientDao.saveOrUpdateClient(client2);
//        ClientDao.deleteClient(ClientDao.getClientById(3));
        // 2. display clients
        ClientDao.getClientsDTO().stream().forEach(System.out::println);

        // IV. Itinerary - to be changed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
        Itinerary itinerary1 = new Itinerary(1, "Sofia, Obikolna street, 21",
                "Gabrovo, Stoletov street, 15", LocalDate.of(2024, 05, 21),
                LocalDate.of(2024, 05, 22));
        Itinerary itinerary2 = new Itinerary(2,"Varna, Pirin street 26",
                "Plovdiv, Kukush street 3", LocalDate.of(2024, 06, 12),
                LocalDate.of(2024, 06, 12));

        ItineraryDao.saveOrUpdateItinerary(itinerary1);
        ItineraryDao.saveOrUpdateItinerary(itinerary2);
//        ItineraryDao.saveOrUpdateItinerary(new Itinerary());
//        ItineraryDao.deleteItinerary(ItineraryDao.getItineraryById(3));

        // V. VehicleType and Vehicle
        // 1. creating vehicle types
        VehicleType vehicleType1 = new VehicleType(1, "Bus");
        VehicleType vehicleType2 = new VehicleType(2, "Truck");
        VehicleType vehicleType3 = new VehicleType(3, "Boat");
        VehicleType vehicleType4 = new VehicleType(4, "Plane");

        VehicleTypeDao.saveOrUpdateVehicleType(vehicleType1);
        VehicleTypeDao.saveOrUpdateVehicleType(vehicleType2);
        VehicleTypeDao.saveOrUpdateVehicleType(vehicleType3);
        VehicleTypeDao.saveOrUpdateVehicleType(vehicleType4);

        VehicleTypeDao.getVehicleTypes().stream().forEach(System.out::println);

        // 2. creating vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setCompany(CompanyDao.getCompanyById(1));
        vehicle1.setVehicleType(VehicleTypeDao.getVehicleTypeById(1));
        VehicleDao.saveOrUpdateVehicle(vehicle1);
//        VehicleDao.addVehicle(new Vehicle());
//        VehicleDao.deleteVehicle(VehicleDao.getVehicleById(2));
        VehicleDao.getVehicles().stream().forEach(System.out::println);
////
//        System.out.println("Vehicle Type: ");
//        VehicleDao.getVehicleType(vehicle1);

        // VI. Let's try GenericDao:
        Passenger passenger1 = new Passenger(1, "Simona Vel");
        Goods goods1 = new Goods(2,BigDecimal.valueOf(1.2));
        try {
            GenericDao<Passenger> passengerGenericDao = new GenericDao<>();
            passengerGenericDao.saveOrUpdateEntity(passenger1);
            GenericDao<Goods> goodsGenericDao = new GenericDao<>();
            goodsGenericDao.saveOrUpdateEntity(goods1);

            // deletes the row in cargo as well!
            passengerGenericDao.deleteEntity(passenger1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // VII. Driver and DrivingQualifications

        String[] dqArray = {"A2", "B1", "C", "D", "Flammable", "Top Secret"};

        Set<DrivingQualification> allDrivingQualifications = new HashSet<>();
        int i = 1;
        for (String dq : dqArray) {
            allDrivingQualifications.add(new DrivingQualification(i, dq));
            i++;
        }

        allDrivingQualifications.forEach(System.out::println);

        for (DrivingQualification dq : allDrivingQualifications)
            DrivingQualificationDao.saveOrUpdateDrivingQualification(dq);

        Driver driver1 = new Driver(1,"Mysterious private driver");
        // the driver must have a name!
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(2), driver1);
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(6), driver1);

    }
}