package com.pineframework.core.mongodb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

import static javax.persistence.Persistence.createEntityManagerFactory;

/**
 * Create entity manage factory.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class HibernateOgmUtils {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        ENTITY_MANAGER_FACTORY = createEntityManagerFactory("jpa-ogm-mongo-pine");
    }

    public static EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void closeApplication() {
        ENTITY_MANAGER_FACTORY.close();
    }

    public static TransactionManager getJtaImpl() {
        return com.arjuna.ats.jta.TransactionManager.transactionManager();
    }
}