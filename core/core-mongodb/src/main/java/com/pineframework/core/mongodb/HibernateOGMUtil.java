//package com.pineframework.core.mongodb;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.transaction.TransactionManager;
//
//public class HibernateOGMUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateOGMUtil.class);
//
//    private static EntityManagerFactory entityManagerFactory = null;
//
//    static {
//        try {
//            entityManagerFactory = Persistence.createEntityManagerFactory("jpa-ogm-mongo-pine");
//
//        } catch (Exception e) {
//			LOGGER.error(e.getMessage());
//        }
//    }
//
//    public static EntityManager createEntityManager() {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    public static void closeEntityManagerFactory() {
//        entityManagerFactory.close();
//    }
//
//    public static TransactionManager getJTA() {
//        return com.arjuna.ats.jta.TransactionManager.transactionManager();
//    }
//}