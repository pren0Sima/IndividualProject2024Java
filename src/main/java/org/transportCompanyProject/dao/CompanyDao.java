package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.entity.Company;

import java.util.List;

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

}
