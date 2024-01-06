package org.transportCompanyProject;

import org.transportCompanyProject.Enumerations.PositionType;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dao.*;
import org.transportCompanyProject.entity.*;
import org.transportCompanyProject.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // I. Company.
        // 1. creating the database with all tables(without rows)
        SessionFactoryUtil.getSessionFactory().openSession();
//        // 2. adding a company in the company table:
        Company company1 = new Company(1, "Greco");
        Company company2 = new Company(2,"Orbico");
        Company company3 = new Company(3, "Telenor");
        Company company4 = new Company(4,"Union Ivkoni");
        CompanyDao.saveOrUpdateCompany(company1);
        CompanyDao.saveOrUpdateCompany(company2);
        CompanyDao.saveOrUpdateCompany(company3);
        CompanyDao.saveOrUpdateCompany(company4);

        /*
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

*/

        // Let's try again.
        // II. let's add employees. Again.
        Employee employee3 = new Employee(1, "Linda The Smartest", PositionType.ADMINISTRATOR, BigDecimal.valueOf(4200));
        employee3.setCompany(CompanyDao.getCompanyById(1));
        Employee employee4 = new Employee(2, "Johan Naz", PositionType.MANAGER, BigDecimal.valueOf(3000));
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
        Vehicle bus1 = new Vehicle();
        bus1.setId(1);
        bus1.setCompany(CompanyDao.getCompanyById(4));
        bus1.setVehicleType(VehicleTypeDao.getVehicleTypeById(1));
        VehicleDao.saveOrUpdateVehicle(bus1);
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

        List<DrivingQualification> allDrivingQualifications = new ArrayList<>();
        int i = 1;
        for (String dq : dqArray) {
            allDrivingQualifications.add(new DrivingQualification(i, dq));
            i++;
        }

        for (DrivingQualification dq : allDrivingQualifications)
            DrivingQualificationDao.saveOrUpdateDrivingQualification(dq);

        allDrivingQualifications.forEach(System.out::println);

        Driver driver1 = new Driver(1,"Mysterious private driver");
        // the driver must have a name!
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(2), driver1);
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(6), driver1);


        // VIII. criteria queries
        // 1. Add companies in db
        Company nestle1 = new Company(5,"Nestle 1");
        Company nestle2 = new Company(6,"Nestle 2");
        CompanyDao.saveOrUpdateCompany(nestle1);
        CompanyDao.saveOrUpdateCompany(nestle2);

        // 2. Check for companies starting with a string:
        CompanyDao.companiesFindByNameStartingWith("Nestle").forEach(System.out::println);

        // 3. Check for companies that have an income between 10000 and 50000
            // 3.1. manipulate the data
                // 3.1.1. set balance
        company1.setBalance(BigDecimal.valueOf(40000));
        company2.setBalance(BigDecimal.valueOf(1000));
        company3.setBalance(BigDecimal.valueOf(140000));
        company4.setBalance(BigDecimal.valueOf(30000));
        nestle1.setBalance(BigDecimal.valueOf(100000));
        nestle2.setBalance(BigDecimal.valueOf(10000));
                // 3.1.2. set overcharge
        company1.setOvercharge(BigDecimal.valueOf(0.3));
        company2.setOvercharge(BigDecimal.valueOf(0.4));
        company3.setOvercharge(BigDecimal.valueOf(0.45));
        company4.setOvercharge(BigDecimal.valueOf(0.5));
        nestle1.setOvercharge(BigDecimal.valueOf(0.35));
        nestle2.setOvercharge(BigDecimal.valueOf(0.25));

        CompanyDao.saveOrUpdateCompany(company1);
        CompanyDao.saveOrUpdateCompany(company2);
        CompanyDao.saveOrUpdateCompany(company3);
        CompanyDao.saveOrUpdateCompany(company4);
        CompanyDao.saveOrUpdateCompany(nestle1);
        CompanyDao.saveOrUpdateCompany(nestle2);

        CompanyDao.companiesFindByBalanceBetween(BigDecimal.valueOf(10000), BigDecimal.valueOf(50000)).forEach(System.out::println);
        // 4. Order by income
        CompanyDao.getOrderedCompaniesByBalance().forEach(System.out::println);
        // 5. Order by name
        CompanyDao.getOrderedCompaniesByName().forEach(System.out::println);
        // 6. Order by name and income
        CompanyDao.findByNameStartingWithAndBalanceGreaterThan("Nestle", BigDecimal.valueOf(9000)).forEach(System.out::println);
        // 7. Queries for Employees:
            // 7.1. create employees
            Employee employee1 = new Employee(3, "Johannes Probs", PositionType.JANITOR, BigDecimal.valueOf(1200));
            Employee employee2 = new Employee(4, "Benjamin Lao", PositionType.ADMINISTRATOR, BigDecimal.valueOf(3200));
            EmployeeDao.saveOrUpdateEmployee(employee1);
            EmployeeDao.saveOrUpdateEmployee(employee2);
            // 7.2. find by salary
        EmployeeDao.employeesFindBySalaryBetween(BigDecimal.valueOf(3000), BigDecimal.valueOf(4000)).forEach(System.out::println);
            // 7.3. find by name start - not wanted
        EmployeeDao.employeesFindByNameStartingWith("Johan").forEach(System.out::println);
            // 7.4. order employees by salary(ACS):
        EmployeeDao.getOrderedEmployeesBySalaryASC().forEach(System.out::println);
            // 7.5. order employees by salary(DESC):
        EmployeeDao.getOrderedEmployeesBySalaryDESC().forEach(System.out::println);
            // 7.6. order employees by position(ASC):
        EmployeeDao.getOrderedEmployeesByPositionASC().forEach(System.out::println);
            // 7.7. order employees by position(ASC) and salary(DESC):
        EmployeeDao.getOrderedEmployeesByASCPositionANDDESCSalary().forEach(System.out::println);
            // 7.8. find employees that have a certain positionType:
        EmployeeDao.employeesFindByPosition(PositionType.ADMINISTRATOR).forEach(System.out::println);


            // 8. find itineraries with the same destination:
        Itinerary itinerary3 = new Itinerary(3,"Sofia, Cherni Vrah boulevard 17",
                "Plovdiv, Kukush street 12", LocalDate.of(2024, 07, 21),
                LocalDate.of(2024, 07, 23));
        ItineraryDao.saveOrUpdateItinerary(itinerary3);
        // 8.1. 2 to Plovdiv:
        ItineraryDao.itinerariesFindByDestination("Plovdiv").forEach(System.out::println);
        // 8.2. none to Shumen:
        ItineraryDao.itinerariesFindByDestination("Shumen").forEach(System.out::println);

            // 9. order itineraries by destination:
        ItineraryDao.getOrderedItinerariesByDestination().forEach(System.out::println);

        // IX. Trying out Obligation with GenericDao - works fine. for now.
        GenericDao<Obligation> obligationGenericDao = new GenericDao<>();
//        Obligation obligation1 = new Obligation();
//        obligationGenericDao.saveOrUpdateEntity(obligation1);

        // X. TODO: make the whole process:
        // 1.1. Add costs for the itineraries.
        itinerary1.setCost(BigDecimal.valueOf(500));
        itinerary2.setCost(BigDecimal.valueOf(450));
        itinerary3.setCost(BigDecimal.valueOf(200));
        // 1.2. TODO: Add clients to the itineraries.
        itinerary1.setClient(client2);
        itinerary2.setClient(client2);
        itinerary3.setClient(client1);
        // 1.3. TODO: Add drivers to the itineraries.
        Driver driver2 = new Driver(2,
                "Denis Vasilev");
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(2), driver2);
        // when creating the itinerary, it adds the driver
        itinerary1.setDriver(driver2);
        itinerary2.setDriver(driver2);
        itinerary3.setDriver(driver1);
        // 1.4. TODO: Add vehicles to the itineraries.
        Vehicle truck1 = new Vehicle(2, CompanyDao.getCompanyById(5), vehicleType2);
        Vehicle boat1 = new Vehicle(3, CompanyDao.getCompanyById(2), vehicleType3);

        VehicleDao.saveOrUpdateVehicle(truck1);
        VehicleDao.saveOrUpdateVehicle(boat1);

        itinerary1.setVehicle(bus1);
        itinerary2.setVehicle(truck1);
        itinerary3.setVehicle(boat1);

        ItineraryDao.saveOrUpdateItinerary(itinerary1);
        ItineraryDao.saveOrUpdateItinerary(itinerary2);
        ItineraryDao.saveOrUpdateItinerary(itinerary3);

        // Let's try out some things:
        // see all vehicles through dto - works fine.
        VehicleDao.getVehiclesDTO().forEach(System.out::println);

        // trying out functions for executeItinerary one:
        try {
            System.out.println("Validating bus1: " + VehicleDao.validateVehicle(bus1));
            // calculations - correct.
            System.out.println("Base price: " + itinerary1.getCost());
            BigDecimal priceToPay = ItineraryDao.calculatePriceWithOvercharge(itinerary1.getCost(),
                    ItineraryDao.getCompanyOverchargeThroughItinerary(itinerary1));
            System.out.println("Calculating price for client: " + priceToPay);
//            Vehicle vehicle = new Vehicle();
//            System.out.println("Validating empty vehicle's company: " + VehicleDao.validateVehicle(vehicle));
            System.out.println("Validation client1 through bd: " + ClientDao.validateClient(ClientDao.getClientById(1)));
            System.out.println("Validation for itinerary: " + ItineraryDao.validateItinerary(itinerary1));

            System.out.println("Check if a client can pay for itinerary: " + ClientDao.canAClientPay(priceToPay, client1));
        } catch (Exception e) {
            System.err.println(e);
        }


        // The moment of truth. Let's try out to execute an itinerary.
        try {
            ItineraryDao.executeItinerary(itinerary1);
        } catch (Exception e) {
            System.err.println(e);
        }


        // see all Itinerary details - works fine.
//        ItineraryDao.getItinerariesDTO().forEach(System.out::println);
//        List<ItineraryDto> itinerariesList = ItineraryDao.getItinerariesDTO();

        // 1.5. TODO: Make a void method for executing an itinerary in which:
                                    // ItineraryDao.executeItinerary(itinerary){try catch for the type of exception thrown
                // 1.5.1. TODO: We must make an obligation.
        //                       $$$    If there is no client and no itinerary => exception
                                        // validateClient() and validateItinerary()
                                        // Obligation obligation = new Obligation(itinerary)
                                        // ObligationDao.saveOrUpdate(obligation)
                // 1.5.2. TODO: We must check if the vehicle is connected to a company. If not => exception
        //                       $$$
        //                                   // VehicleDao.validateVehicle(itinerary.getVehicle()){
        //                                      if (vehicle.getCompany == null)
        //                                      => throw new VehicleHasNoCompanyException() }
                                                                                        // If yes => Company
                // 1.5.3. TODO: We must check the funds of the client. In not enough => don't change the paid to true at the end
                           //    $$$    // priceWithOvercharge = BigDecimal calculatePriceWithOvercharge(basePrice, overcharge)
                                        // bool ClientDao.canAClientPay(priceWithOvercharge, itinerary.getClient()) {
        //                                      if (Client.getBalance.compareTo(priceWithOvercharge) < 0)
        //                                      => return false;
                        //                      else return true;
                // 1.5.4. TODO: If all is good: if (validateClientsFunds == true)=>
        //                                      1) TODO: take money from client's balance,
                                        // makePayment(client, company, amount, obligation) {
        //                                      ClientDao.subtractFromBalance(itinerary.getCost().multiply(company.getOvercharge()), itinerary.getClient());
        //                                        2) TODO: add it to the company's balance and
                                        //        CompanyDao.addToBalance(itinerary.getCost(), itinerary.getClient())
        //                                        3) TODO: mark the obligation as paid
                                        //        obligation.setPaid(true); }
    }          // 1.5.5. TODO: If not -> the company pays and the obligation stays
                                        // TODO: bool CompanyDao.validateCompanysFunds(amount, company)
                                                  //  if true => CompanyDao.subtractFromBalance(itinerary.getCost(), company)
                                                  // else => throw new NotEnoughMoneyInCompanyBalanceException()

}