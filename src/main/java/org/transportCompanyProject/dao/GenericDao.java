package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import java.util.List;

// using it for Cargo, Passenger, Goods, Driver, DrivingQualifications, ...
// for adding, getting by id, saving or updating, deleting and getting all entities (CRUD)
public class GenericDao<T> {

    public void addEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    public T getEntityById(long id, Class<T> entityClass) {
        T entity;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entity = session.get(entityClass, id);
            transaction.commit();
        }
        return entity;
    }

    public void saveOrUpdateEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    public void deleteEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }

    public List<T> getEntities(Class<T> entityClass) {
        List<T> entities;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entities = session.createQuery("Select e From " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
            transaction.commit();
        }
        return entities;
    }
}
