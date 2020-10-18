package com.pineframework.core.tutorial.eshop.testmongodb;

import org.hibernate.ogm.jpa.HibernateOgmPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Summary
 * <p>
 * Description
 *
 * @author Burak Cekil (burakcekil.com)
 * @version 1.0
 * @since 4/18/2020
 */

@Configuration
@EnableTransactionManagement
public class OrmConfig {

    public Map<String, String> connectionProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.ogm.datastore.provider", "mongodb");
        properties.put("hibernate.ogm.datastore.host", "localhost");
        properties.put("hibernate.ogm.datastore.port", "27017");
        properties.put("hibernate.ogm.datastore.database", "pine");
        properties.put("hibernate.ogm.datastore.create_database", "true");
        return properties;
    }

    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.pineframework.core.tutorial.eshop.testmongodb");
        emf.setPersistenceUnitName("pine-persistUnit");
        emf.setJpaPropertyMap(connectionProperties());
        emf.setPersistenceProviderClass(HibernateOgmPersistence.class);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

}
