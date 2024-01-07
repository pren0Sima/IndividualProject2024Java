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
/**
 * Data Access Object (DAO) class for managing Company entities in the database.
 */
public class CompanyDao {
    /**
     * Adds a new company to the database.
     *
     * @param company The Company object to be added.
     */
    public static void addCompany(Company company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(company);
            transaction.commit();
        }
    }
    /**
     * Retrieves a company by its id from the database.
     *
     * @param id The id of the Company to be retrieved.
     * @return The Company object with the specified id, or null if not found.
     */
    public static Company getCompanyById(long id) {
        Company company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }
    /**
     * Saves or updates an existing company in the database.
     *
     * @param company The Company object to be saved or updated.
     */
    public static void saveOrUpdateCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(company);
            transaction.commit();
        }
    }
    /**
     * Deletes a company from the database.
     *
     * @param company The Company object to be deleted.
     */
    public static void deleteCompany(Company company){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(company);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all companies from the database.
     *
     * @return List of all companies in the database.
     */
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
    /**
     * Retrieves a list of CompanyDto objects containing specific information about companies.
     *
     * @return List of CompanyDto objects.
     */
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

    /**
     * Counts the total number of companies in the database.
     *
     * @return The number of companies in the database.
     */
    public static int countCompanies(){
        return getCompanies().size();
    }

    /**
     * Retrieves the set of employees associated with a specific company from the database with a join fetch query.
     *
     * @param id The id of the company.
     * @return Set of employees associated with the specified company.
     */
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

    /**
     * Retrieves a list of EmployeeDto objects containing specific information about employees of a company.
     *
     * @param company_id The id of the company.
     * @return List of EmployeeDto objects.
     */
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

    /**
     * Retrieves a list of companies with balances between the specified bottom and top values.
     *
     * @param bottom The lower bound of the balance range.
     * @param top    The upper bound of the balance range.
     * @return List of companies with balances within the specified range.
     */
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
    /**
     * Retrieves a list of companies with names starting with the specified string.
     *
     * @param name The starting string of the company names.
     * @return List of companies with names starting with the specified string.
     */
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
    /**
     * Retrieves a list of companies ordered by balance in ascending order.
     *
     * @return List of companies ordered by balance in ascending order.
     */
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
    /**
     * Retrieves a list of companies ordered by name in ascending order.
     *
     * @return List of companies ordered by name in ascending order.
     */
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
    /**
     * Retrieves a list of companies with names starting with the specified string and balance greater than the specified value.
     *
     * @param name    The starting string of the company names.
     * @param balance The minimum balance value.
     * @return List of companies with names starting with the specified string and balance greater than the specified value.
     */
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
    /**
     * Checks if a company can pay a specified price based on its balance.
     *
     * @param price   The price to be checked against the company's balance.
     * @param company The Company object whose balance is checked.
     * @return true if the company's balance is greater than or equal to the specified price.
     */
    public static boolean canACompanyPay(BigDecimal price, Company company) {
        if (company.getBalance().compareTo(price) < 0)
            return false;
        else
            return true;
    }
    /**
     * Adds a specified amount to a company's balance.
     *
     * @param amount  The amount to be added to the company's balance.
     * @param company The Company object whose balance is updated.
     * @throws AmountShouldBePositiveException If the specified amount is not a positive BigDecimal value.
     */
    public static void addToBalance(BigDecimal amount, Company company) throws AmountShouldBePositiveException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountShouldBePositiveException("Amount should be a positive BigDecimal value!");
        }
        // change the object
        company.setBalance(company.getBalance().add(amount));
        // save the change into the db
        saveOrUpdateCompany(company);
    }
/**
 * Subtracts a specified amount from a company's balance.
 *
 * @param amount  The amount to be subtracted from the company's balance.
 * @param company The Company object whose balance is updated.
 * @throws AmountShouldBePositiveException if the specified amount is not a positive BigDecimal value.
 */
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
