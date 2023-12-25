package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.CompanyDto;
import org.transportCompanyProject.dto.EmployeeDto;
import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.Employee;
import org.transportCompanyProject.interfaces.Accounting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class CompanyDao implements Accounting {
    public static void addCompany(Company company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(company);
            transaction.commit();
        }
    }
    public static Company getCompanyById(long id) {
        Company company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }
    public static void updateCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(company);
            transaction.commit();
        }
    }
    public static List<Company> getCompanies() {
        List<Company> companies;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("Select c From Company c", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }
    public static List<CompanyDto> getCompaniesDTO() {
        List<CompanyDto> companies;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session
                    .createQuery("select new org.transportCompanyProject.dto.CompanyDto(c.id, c.name) " +
                            "from Company c", CompanyDto.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }
    // delete company
    public static void deleteCompany(Company company){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(company);
            transaction.commit();
        }
    }
    // count all companies
    public static int countCompanies(){
        return getCompanies().size();
    }

// Lection 7
    // 1st method:
    public static Set<Employee> getCompanyEmployees(long id) {
        Company company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.createQuery(
                    "select c from Company c" +
                            " join fetch c.employees" +
                            " where c.id = :id",
                    Company.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();
        }
        return company.getEmployees();
    }

    // 2nd method: DTO - data transfer object
    public static List<EmployeeDto> getCompanyEmployeesDTO(long id) {
        List<EmployeeDto> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery(
                    "select new org.transportCompanyProject.dto.EmployeeDto(e.id, e.name, e.positionType, e.company) from Employee e" +
                            " join e.company c" +
                            " where c.id = :id",
                    EmployeeDto.class)
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }


    // from Accounting interface
    @Override
    public void addToExpenses(BigDecimal amount, Company company) {
        // change the object
        company.setExpenses(company.getExpenses().add(amount));
        // save the change into the db
        updateCompany(company);
    }

    @Override
    public void addToIncome(BigDecimal amount, Company company) {
        // change the object
        company.setIncome(company.getIncome().add(amount));
        // save the change into the db
        updateCompany(company);
    }

    @Override
    public BigDecimal calculateProfit(Company company) {
        // profit = income - expenses
        return company.getIncome().subtract(company.getExpenses());
    }
}
