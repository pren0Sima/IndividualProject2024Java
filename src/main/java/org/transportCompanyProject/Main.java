package org.transportCompanyProject;

import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dao.CompanyDao;
import org.transportCompanyProject.dao.EmployeeDao;
import org.transportCompanyProject.dto.CompanyDto;
import org.transportCompanyProject.dto.EmployeeDto;
import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.Employee;
import org.transportCompanyProject.entity.PositionType;

import java.math.BigDecimal;

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
        CompanyDao.updateCompany(company1);
        CompanyDao.updateCompany(company2);
        CompanyDao.updateCompany(company3);
        CompanyDao.updateCompany(company4);
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
//        CompanyDao.getCompanyEmployeesDTO(6)
//                .stream().forEach(System.out::println);


        // Let's try again.
        // let's add employees. Again.
        Employee employee3 = new Employee(1, "Kiril Simeonov Velichkov", PositionType.ADMINISTRATOR, BigDecimal.valueOf(2000));
        employee3.setCompany(CompanyDao.getCompanyById(1));
        Employee employee4 = new Employee(2, "Pavlina Velichkova", PositionType.MANAGER, BigDecimal.valueOf(2000));
        employee4.setCompany(CompanyDao.getCompanyById(2));
        EmployeeDao.updateEmployee(employee3);
        EmployeeDao.updateEmployee(employee4);
    }
}