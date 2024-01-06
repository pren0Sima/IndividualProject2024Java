package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.Obligation;

import java.util.List;

public class ObligationDao {
    public static void addObligation(Obligation obligation) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(obligation);
            transaction.commit();
        }
    }

    public static void deleteObligation(Obligation obligation){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(obligation);
            transaction.commit();
        }
    }
    public static Obligation getObligationById(long id) {
        Obligation obligation;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            obligation = session.get(Obligation.class, id);
            transaction.commit();
        }
        return obligation;
    }
    public static void saveOrUpdateObligation(Obligation obligation) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(obligation);
            transaction.commit();
        }
    }
    public static List<Obligation> getObligations() {
        List<Obligation> obligations;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            obligations = session.createQuery("Select o From Obligation o", Obligation.class)
                    .getResultList();
            transaction.commit();
        }
        return obligations;
    }
}
