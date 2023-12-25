package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.entity.Employee;

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
}
