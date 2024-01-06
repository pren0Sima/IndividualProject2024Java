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

public class EmployeeDao {
    public static void addEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(employee);
            transaction.commit();
        }
    }
    public static Employee getEmployeeById(long id) {
        Employee employee;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }
    public static void saveOrUpdateEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(employee);
            transaction.commit();
        }
    }
    public static void deleteEmployee(Employee employee){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(employee);
            transaction.commit();
        }
    }
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

    // Criteria queries
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
    // find employees by position
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
    // get ordered employees by salaries:
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

    // descending order (highest to lowest)
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

    // get employees ordered by position
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

    // get employees employees ordered by position and DESC salary(highest to lowest)
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
