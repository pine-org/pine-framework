package com.pineframework.core.transaction;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.pineframework.core.contract.config.TransactionaBeanFactory;
import net.sf.ehcache.transaction.manager.TransactionManagerLookup;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
@ComponentScan(value = "com.pineframework.core")
@Profile(value = {"mysql", "activemq"})
public class MySqlAndActiveMqJtaConfiguration {

    @Autowired
    private Environment environment;

    public void tailorProperties(Properties properties) {
        properties.setProperty("hibernate.transaction.manager_lookup_class", TransactionManagerLookup.class.getName());
    }

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(1000);
        return userTransactionImp;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public TransactionManager transactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(this.environment.getProperty("spring.dataSource.url"));
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(this.environment.getProperty("spring.dataSource.password"));
        mysqlXaDataSource.setUser(this.environment.getProperty("spring.dataSource.password"));

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("xads");
        return xaDataSource;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(this.environment.getProperty("spring.activemq.broker-url"));
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws Throwable {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        return jmsTemplate;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() throws Throwable {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        Properties properties = new Properties();
        tailorProperties(properties);
        entityManager.setJpaProperties(properties);
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() throws Throwable {
        return new JtaTransactionManager(userTransaction(), transactionManager());
    }
}