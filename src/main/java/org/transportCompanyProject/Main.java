package org.transportCompanyProject;

import org.transportCompanyProject.Enumerations.PositionType;
import org.transportCompanyProject.models.dao.*;
import org.transportCompanyProject.models.dto.ItineraryDto;
import org.transportCompanyProject.models.entity.*;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.reports.Report;
import org.transportCompanyProject.models.reports.ReportMaker;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // I. Company.
        // 1. creating the database with all tables(without rows)
        SessionFactoryUtil.getSessionFactory().openSession();
        // 2. adding a company in the company table:
        Company company1 = new Company(1, "Greco");
        Company company2 = new Company(2, "Orbico");
        Company company3 = new Company(3, "Telenor");
        Company company4 = new Company(4, "Union Ivkoni");
        CompanyDao.saveOrUpdateCompany(company1);
        CompanyDao.saveOrUpdateCompany(company2);
        CompanyDao.saveOrUpdateCompany(company3);
        CompanyDao.saveOrUpdateCompany(company4);

        // II. Let's add employees.
        Employee employee1 = new Employee(1, "Johannes Probs", PositionType.JANITOR, BigDecimal.valueOf(1200));
        Employee employee2 = new Employee(2, "Benjamin Lao", PositionType.ADMINISTRATOR, BigDecimal.valueOf(3200));
        Employee employee3 = new Employee(3, "Linda The Smartest", PositionType.ADMINISTRATOR, BigDecimal.valueOf(4200));
        Employee employee4 = new Employee(4, "Johan Naz", PositionType.MANAGER, BigDecimal.valueOf(3000));

        employee1.setCompany(CompanyDao.getCompanyById(3));
        employee2.setCompany(CompanyDao.getCompanyById(4));
        employee4.setCompany(CompanyDao.getCompanyById(2));
        employee3.setCompany(CompanyDao.getCompanyById(1));

        EmployeeDao.saveOrUpdateEmployee(employee1);
        EmployeeDao.saveOrUpdateEmployee(employee2);
        EmployeeDao.saveOrUpdateEmployee(employee3);
        EmployeeDao.saveOrUpdateEmployee(employee4);
        EmployeeDao.getEmployeesDTO().stream().forEach(System.out::println);

        // III. Client
        // 1. add clients
        Client client1 = new Client(1, "Samantha");
        Client client2 = new Client(2, "Lidl");
        ClientDao.saveOrUpdateClient(client1);
        ClientDao.saveOrUpdateClient(client2);
//        ClientDao.deleteClient(ClientDao.getClientById(3));
        // 2. display clients
        ClientDao.getClientsDTO().stream().forEach(System.out::println);

        // IV. Itinerary
        Itinerary itinerary1 = new Itinerary(1, "Sofia, Obikolna street, 21",
                "Gabrovo, Stoletov street, 15", LocalDate.of(2023, 05, 21),
                LocalDate.of(2023, 05, 22));
        Itinerary itinerary2 = new Itinerary(2, "Varna, Pirin street 26",
                "Plovdiv, Kukush street 3", LocalDate.of(2023, 06, 12),
                LocalDate.of(2023, 06, 12));
        Itinerary itinerary3 = new Itinerary(3, "Sofia, Cherni Vrah boulevard 17",
                "Plovdiv, Kukush street 12", LocalDate.of(2024, 07, 21),
                LocalDate.of(2024, 07, 23));

        ItineraryDao.saveOrUpdateItinerary(itinerary1);
        ItineraryDao.saveOrUpdateItinerary(itinerary2);
        ItineraryDao.saveOrUpdateItinerary(itinerary3);
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
        Vehicle bus1 = new Vehicle(1, CompanyDao.getCompanyById(4), VehicleTypeDao.getVehicleTypeById(1));
        Vehicle truck1 = new Vehicle(2, CompanyDao.getCompanyById(5), vehicleType2);
        Vehicle boat1 = new Vehicle(3, CompanyDao.getCompanyById(2), vehicleType3);
        VehicleDao.saveOrUpdateVehicle(bus1);
        VehicleDao.saveOrUpdateVehicle(truck1);
        VehicleDao.saveOrUpdateVehicle(boat1);
//        VehicleDao.addVehicle(new Vehicle());
//        VehicleDao.deleteVehicle(VehicleDao.getVehicleById(2));
        System.out.println("getVehicles() function: ");
        VehicleDao.getVehicles().forEach(System.out::println);
//        System.out.println("Vehicle Type: ");
//        VehicleDao.getVehicleType(vehicle1);

        // VI. Let's try GenericDao:
        Passenger passenger1 = new Passenger(1, "Simona Vel");
        Goods goods1 = new Goods(2, BigDecimal.valueOf(1.2));
        GenericDao<Passenger> passengerGenericDao = new GenericDao<>();
        passengerGenericDao.saveOrUpdateEntity(passenger1);
        GenericDao<Goods> goodsGenericDao = new GenericDao<>();
        goodsGenericDao.saveOrUpdateEntity(goods1);
        try {
            System.out.println("Cargo list:\n");
            CargoDao.getFullCargoList().forEach(System.out::println);
            // deletes the row in cargo as well!
//            passengerGenericDao.deleteEntity(passenger1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // VII. Driver and DrivingQualifications
        // 1. DrivingQualifications
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
        // 2. Driver
        Driver driver1 = new Driver(1, "Mysterious private driver");
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(2), driver1);
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(6), driver1);
        Driver driver2 = new Driver(2, "Denis Vasilev");
        DriverDao.addDrivingQualificationToDriver(DrivingQualificationDao.getDrivingQualificationById(2), driver2);

        // VIII. criteria queries
        // 1. Add companies in db
        Company nestle1 = new Company(5, "Nestle 1");
        Company nestle2 = new Company(6, "Nestle 2");
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
                // 3.1.3. save to db
        CompanyDao.saveOrUpdateCompany(company1);
        CompanyDao.saveOrUpdateCompany(company2);
        CompanyDao.saveOrUpdateCompany(company3);
        CompanyDao.saveOrUpdateCompany(company4);
        CompanyDao.saveOrUpdateCompany(nestle1);
        CompanyDao.saveOrUpdateCompany(nestle2);
        // 3. *written above*
        CompanyDao.companiesFindByBalanceBetween(BigDecimal.valueOf(10000), BigDecimal.valueOf(50000)).forEach(System.out::println);
        // 4. Order by income
        CompanyDao.getOrderedCompaniesByBalance().forEach(System.out::println);
        // 5. Order by name
        CompanyDao.getOrderedCompaniesByName().forEach(System.out::println);
        // 6. Order by name and income
        CompanyDao.findByNameStartingWithAndBalanceGreaterThan("Nestle", BigDecimal.valueOf(9000)).forEach(System.out::println);
        // 7. Queries for Employees:
            // 7.1. find by salary
        EmployeeDao.employeesFindBySalaryBetween(BigDecimal.valueOf(3000), BigDecimal.valueOf(4000)).forEach(System.out::println);
            // 7.2. find by name start - not wanted
        EmployeeDao.employeesFindByNameStartingWith("Johan").forEach(System.out::println);
            // 7.3. order employees by salary(ACS):
        EmployeeDao.getOrderedEmployeesBySalaryASC().forEach(System.out::println);
            // 7.4. order employees by salary(DESC):
        EmployeeDao.getOrderedEmployeesBySalaryDESC().forEach(System.out::println);
            // 7.5. order employees by position(ASC):
        EmployeeDao.getOrderedEmployeesByPositionASC().forEach(System.out::println);
            // 7.6. order employees by position(ASC) and salary(DESC):
        EmployeeDao.getOrderedEmployeesByASCPositionANDDESCSalary().forEach(System.out::println);
            // 7.7. find employees that have a certain positionType:
        EmployeeDao.employeesFindByPosition(PositionType.ADMINISTRATOR).forEach(System.out::println);


        // 8. find itineraries with the same destination:
            // 8.1. 2 to Plovdiv:
        ItineraryDao.itinerariesFindByDestination("Plovdiv").forEach(System.out::println);
            // 8.2. none to Shumen:
        ItineraryDao.itinerariesFindByDestination("Shumen").forEach(System.out::println);

        // 9. order itineraries by destination:
        ItineraryDao.getOrderedItinerariesByDestination().forEach(System.out::println);

        // IX. Trying out Obligation with GenericDao - works fine.
//        GenericDao<Obligation> obligationGenericDao = new GenericDao<>();
//        Obligation obligation1 = new Obligation();
//        obligationGenericDao.saveOrUpdateEntity(obligation1);

        // X. The whole process for executing an itinerary
        // 1.1. Add costs for the itineraries.
        itinerary1.setCost(BigDecimal.valueOf(500));
        itinerary2.setCost(BigDecimal.valueOf(450));
        itinerary3.setCost(BigDecimal.valueOf(200));
        // 1.2. Add clients to the itineraries.
        itinerary1.setClient(client2);
        itinerary2.setClient(client2);
        itinerary3.setClient(client1);
        // 1.3. Add drivers to the itineraries.
        // when creating the itinerary, it adds the driver
        itinerary1.setDriver(driver2);
        itinerary2.setDriver(driver2);
        itinerary3.setDriver(driver1);
        // 1.4. Add vehicles to the itineraries.
        itinerary1.setVehicle(bus1);
        itinerary2.setVehicle(truck1);
        itinerary3.setVehicle(boat1);

        // 1.5. Giving each itinerary some Cargo:
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(1), itinerary1);
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(2), itinerary1);
        System.out.println("Cargo for itinerary 1: ");
        itinerary1.getCargo().stream().forEach(System.out::println);
            // 1.5.1. Adding cargo to itinerary 2:
                // 1. creating cargo:
        Passenger passenger2 = new Passenger(3, "Ivanka Shehova");
        Passenger passenger3 = new Passenger(4, "Shopi Shopov");
        Passenger passenger4 = new Passenger(5, "Sofia Ivanova");
                // 2. adding cargo to the db:
        passengerGenericDao.saveOrUpdateEntity(passenger2);
        passengerGenericDao.saveOrUpdateEntity(passenger3);
        passengerGenericDao.saveOrUpdateEntity(passenger4);
                // 3. connecting the cargo to the itinerary
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(3), itinerary2);
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(4), itinerary2);
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(5), itinerary2);
            // 1.5.2. Adding cargo to itinerary 2:
                // 1. creating cargo:
        Goods goods2 = new Goods(6, BigDecimal.valueOf(0.5));
        Goods goods3 = new Goods(7, BigDecimal.valueOf(0.2));
                // 2. adding cargo to the db:
        goodsGenericDao.saveOrUpdateEntity(goods2);
        goodsGenericDao.saveOrUpdateEntity(goods3);
                // 3. connecting the cargo to the itinerary
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(6), itinerary3);
        ItineraryDao.addCargoToItinerary(CargoDao.getCargoById(7), itinerary3);
        // 1.5. *the thing*
        ItineraryDao.saveOrUpdateItinerary(itinerary1);
        ItineraryDao.saveOrUpdateItinerary(itinerary2);
        ItineraryDao.saveOrUpdateItinerary(itinerary3);
        // 1 way: creating a list with the itineraries (used later)
        List<Itinerary> itineraryList = new ArrayList<>();
        itineraryList.add(itinerary1);
        itineraryList.add(itinerary2);
        itineraryList.add(itinerary3);
        // 2 way: use criteria query
        System.out.println("Trying out CargoDao.getItinerarysCargoList() for itinerary1: ");
        CargoDao.getItinerarysCargoList(1).stream().forEach(System.out::println);

        // XI. Let's try out some things:
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


        // The moment of truth. Let's try out to execute an itinerary:
        // When a client doesn't have enough funds but a company does.
//        try {
//            ItineraryDao.executeItinerary(itinerary1);
//        } catch (Exception e) {
//            System.err.println(e);
//        }
        // now let's try it when the client has enough money.
        Client clientCopy = ClientDao.getClientById(itinerary1.getClient().getId());
        clientCopy.setBalance(BigDecimal.valueOf(2000));
        ClientDao.saveOrUpdateClient(clientCopy);

//        try {
//            ItineraryDao.executeItinerary(itinerary1);
//        } catch (Exception e) {
//            System.err.println(e);
//        }

        // XII. Report making
            // 1. entity type:
        try{
            // for single itinerary:
            Report itineraryReport = new Report("itineraryReport.txt");
            ReportMaker.createSingleItineraryReport("Itinerary report\n", itineraryReport, itinerary1);
            // for all itineraries:
            Report itinerariesListReport = new Report("itinerariesReport.txt");
            ReportMaker.createItineraryListReport("Itineraries Report\n", itinerariesListReport, itineraryList);
            // printing the reports:
            System.out.println("\n");
            ReportMaker.printReport(itineraryReport.getReportName());
            System.out.println("\n\n");
            ReportMaker.printReport(itinerariesListReport.getReportName());
        } catch (IOException e) {
            System.err.println("An error occurred during report creation! : " + e);
        }
            // dto type:
        try{
            // fetch the itineraries from the db:
            List<ItineraryDto> itineraryDtoList = ItineraryDao.getItinerariesDTO();
            // for single itinerary:
            Report itineraryDtoReport = new Report("itineraryDtoReport.txt");
            ReportMaker.createSingleItineraryDtoReport("ItineraryDto report\n", itineraryDtoReport, itineraryDtoList.get(0));
            // for all itineraries:
            Report itineraryDtoListReport = new Report("itineraryDtosReport.txt");
            ReportMaker.createItineraryDtoListReport("Itineraries Report\n", itineraryDtoListReport, itineraryDtoList);
//            // printing the reports:
            System.out.println("\n");
            ReportMaker.printReport(itineraryDtoReport.getReportName());
            System.out.println("\n\n");
            ReportMaker.printReport(itineraryDtoListReport.getReportName());
        } catch (IOException e) {
            System.err.println("An error occurred during report creation! : " + e);
        }

        int countOfExecutedItineraries = ItineraryDao.countExecutedItinerariesDTO();
        BigDecimal totalCostOfItineraries = ItineraryDao.totalCostOfExecutedItineraries();
        System.out.println("\nCount of executed itineraries: " + countOfExecutedItineraries);
        System.out.println("\nTotal cost of executed itineraries: " + totalCostOfItineraries);

        // XIII. Point 9: some additional reports:
            // 1. Create a report for a total amount of executed itineraries
                // 1.1. write(int numberOfExecutedItineraries -> if dateOfDeparture.isBefore(LocalDate.now()) count++)) this ain't it, but we can work on it
            // 2. Add to report sum(itinerary cost) of all executed itineraries
            // 3. Add to report number of itineraries executed by a certain driver
            // 4. Add to report company profit for a certain amount of time
            // 5. Add to report how much a company has earned by each driver
    }
}