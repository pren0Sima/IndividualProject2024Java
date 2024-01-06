package org.transportCompanyProject.models.dao;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.CompanyDto;
import org.transportCompanyProject.models.dto.EmployeeDto;
import org.transportCompanyProject.models.entity.Company;
import org.transportCompanyProject.models.entity.Employee;
import org.transportCompanyProject.exceptions.AmountShouldBePositiveException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class CompanyDao {
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
    public static void saveOrUpdateCompany(Company company) {
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
                    .createQuery("select new org.transportCompanyProject.models.dto.CompanyDto(c.id, c.name) " +
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
    public static List<EmployeeDto> getCompanyEmployeesDTO(long company_id) {
        List<EmployeeDto> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery(
                    "select new org.transportCompanyProject.models.dto.EmployeeDto(e.id, e.name, e.positionType, e.company) from Employee e" +
                            " join e.company c" +
                            " where c.id = :id",
                    EmployeeDto.class)
                    .setParameter("id", company_id)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }


    // Criteria queries
    public static List<Company> companiesFindByBalanceBetween(BigDecimal bottom, BigDecimal top) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);

            cr.select(root).where(cb.between(root.get("balance"), bottom, top));

            Query<Company> query = session.createQuery(cr);
            return query.getResultList();
        }
    }
    // company by name
    public static List<Company> companiesFindByNameStartingWith(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);

            Predicate nameStartingWith = cb.like(root.get("name"), name + "%");

            cr.select(root).where(nameStartingWith);

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }
    // sorting by name and income
    public static List<Company> getOrderedCompaniesByBalance() {
        List<Company> companies;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("Select c From Company c" +
                            " ORDER BY c.balance", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }

    public static List<Company> getOrderedCompaniesByName() {
        List<Company> companies;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("Select c From Company c" +
                            " ORDER BY c.name", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }
    // by name and balance
    public static List<Company> findByNameStartingWithAndBalanceGreaterThan(String name, BigDecimal balance) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cr = cb.createQuery(Company.class);
            Root<Company> root = cr.from(Company.class);

            Predicate greaterThanIncome = cb.greaterThan(root.get("balance"), balance);
            Predicate nameStartingWith = cb.like(root.get("name"), name + "%");

            cr.select(root).where(cb.and(nameStartingWith, greaterThanIncome));

            Query<Company> query = session.createQuery(cr);
            List<Company> companies = query.getResultList();
            return companies;
        }
    }
    public static boolean canACompanyPay(BigDecimal price, Company company) {
        if (company.getBalance().compareTo(price) < 0)
            return false;
        else
            return true;
    }
    public static void addToBalance(BigDecimal amount, Company company) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        company.setBalance(company.getBalance().add(amount));
        // save the change into the db
        saveOrUpdateCompany(company);
    }

    public static void subtractFromBalance(BigDecimal amount, Company company) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        company.setBalance(company.getBalance().subtract(amount));
        // save the change into the db
        saveOrUpdateCompany(company);
    }
}
