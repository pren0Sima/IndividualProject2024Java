package org.transportCompanyProject.models.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.EmployeeDto;
import org.transportCompanyProject.models.entity.Employee;
import org.transportCompanyProject.Enumerations.PositionType;

import java.math.BigDecimal;
import java.util.List;
/**
 * Data Access Object (DAO) class for managing Employee entities in the database.
 */
public class EmployeeDao {
    /**
     * Adds a new employee to the database.
     *
     * @param employee The Employee object to be added.
     */
    public static void addEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(employee);
            transaction.commit();
        }
    }
    /**
     * Retrieves an employee by their id from the database.
     *
     * @param id The ID of the Employee to be retrieved.
     * @return The Employee object with the specified ID, or null if not found.
     */
    public static Employee getEmployeeById(long id) {
        Employee employee;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }
    /**
     * Saves or updates an existing employee in the database.
     *
     * @param employee The Employee object to be saved or updated.
     */
    public static void saveOrUpdateEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(employee);
            transaction.commit();
        }
    }
    /**
     * Deletes an employee from the database.
     *
     * @param employee The Employee object to be deleted.
     */
    public static void deleteEmployee(Employee employee){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(employee);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all employees from the database.
     *
     * @return List of all employees in the database.
     */
    public static List<Employee> getEmployees() {
        List<Employee> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery("Select e From Employee e", Employee.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }
    /**
     * Retrieves a list of EmployeeDto objects containing specific employee details.
     *
     * @return List of EmployeeDto objects.
     */
    public static List<EmployeeDto> getEmployeesDTO() {
        List<EmployeeDto> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session
                    .createQuery(
                            "Select new org.transportCompanyProject.models.dto.EmployeeDto(e.id, " +
                                    "e.name, e.positionType, e.company)" +
                            " From Employee e", EmployeeDto.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }

    /**
     * Retrieves a list of employees with salaries within a specified range.
     *
     * @param bottom The lower limit of the salary range.
     * @param top    The upper limit of the salary range.
     * @return List of employees with salaries within the specified range.
     */
    public static List<Employee> employeesFindBySalaryBetween(BigDecimal bottom, BigDecimal top) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);

            cr.select(root).where(cb.between(root.get("salary"), bottom, top));

            Query<Employee> query = session.createQuery(cr);
            return query.getResultList();
        }
    }
    /**
     * Retrieves a list of employees based on their position type.
     *
     * @param positionType The PositionType to filter employees.
     * @return List of employees with the specified position type.
     */
    public static List<Employee> employeesFindByPosition(PositionType positionType) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);

            cr.select(root).where(cb.equal(root.get("positionType"), positionType));

            Query<Employee> query = session.createQuery(cr);
            return query.getResultList();
        }
    }
    /**
     * Retrieves a list of employees ordered by salary in ascending order.
     *
     * @return List of employees ordered by salary in ascending order.
     */
    public static List<Employee> getOrderedEmployeesBySalaryASC() {
        List<Employee> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery("Select c From Employee c" +
                            " ORDER BY c.salary", Employee.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }

    /**
     * Retrieves a list of companies ordered by name in descending order.
     *
     * @return List of companies ordered by name in descending order.
     */
    public static List<Employee> getOrderedEmployeesBySalaryDESC() {
        List<Employee> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery("Select c From Employee c" +
                            " ORDER BY c.salary DESC", Employee.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }

    /**
     * Retrieves a list of companies ordered by position type in ascending order.
     *
     * @return List of companies ordered by positionType in ascending order.
     */
    public static List<Employee> getOrderedEmployeesByPositionASC() {
        List<Employee> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery("Select c From Employee c" +
                            " ORDER BY CONCAT(c.positionType)", Employee.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }
    /**
     * Retrieves a list of companies ordered by position type in ascending order and salaries in descending.
     *
     * @return List of companies ordered by positionType in ascending order and salary in descending order.
     */
    public static List<Employee> getOrderedEmployeesByASCPositionANDDESCSalary () {
        List<Employee> employees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employees = session.createQuery("Select c From Employee c" +
                            " ORDER BY CONCAT(c.positionType), c.salary DESC", Employee.class)
                    .getResultList();
            transaction.commit();
        }
        return employees;
    }
    /**
     * Retrieves a list of employees with names starting with the specified string.
     *
     * @param name The starting string of the employee names.
     * @return List of employees with names starting with the specified string.
     */
    public static List<Employee> employeesFindByNameStartingWith(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);

            Predicate nameStartingWith = cb.like(root.get("name"), name + "%");

            cr.select(root).where(nameStartingWith);

            Query<Employee> query = session.createQuery(cr);
            List<Employee> employees = query.getResultList();
            return employees;
        }
    }
}
