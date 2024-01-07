package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import java.util.List;

/**
 * Generic Data Access Object (DAO) class for performing CRUD operations on entities in the database.
 *
 * @param <T> The type of entity this GenericDao works with.
 */
public class GenericDao<T> {
    /**
     * Adds a new entity to the database.
     *
     * @param entity The entity object to be added.
     */
    public void addEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }
    /**
     * Retrieves an entity by its id from the database.
     *
     * @param id          The id of the entity to be retrieved.
     * @param entityClass The class type of the entity.
     * @return The entity object with the specified id, or null if not found.
     */
    public T getEntityById(long id, Class<T> entityClass) {
        T entity;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entity = session.get(entityClass, id);
            transaction.commit();
        }
        return entity;
    }
    /**
     * Saves or updates an existing entity in the database.
     *
     * @param entity The entity object to be saved or updated.
     */
    public void saveOrUpdateEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }
    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity object to be deleted.
     */
    public void deleteEntity(T entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all entities of a specific type from the database.
     *
     * @param entityClass The class type of the entity.
     * @return List of all entities in the database.
     */
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
