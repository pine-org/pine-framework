package com.pineframework.core.spring.transaction.helper;

import com.pineframework.core.contract.config.TransactionalBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import java.util.Map;
import java.util.Properties;

/**
 * It should be used, when use {@code @Bean} annotation to create a bean.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Component
public class TransactionHelper implements TransactionalBeanFactory {

    @Autowired
    private JtaTransactionManager transactionManager;

    public <T, E> E create(T bean, Class<E> type) {
        TransactionProxyFactoryBean proxy = new TransactionProxyFactoryBean();
        // Inject transaction manager here
        proxy.setTransactionManager(transactionManager);
        // Define wich object instance is to be proxied (your bean)
        proxy.setTarget(bean);
        // Programmatically setup transaction attributes
        Properties transactionAttributes = new Properties();
        transactionAttributes.put("*", "PROPAGATION_REQUIRED");
        proxy.setTransactionAttributes(transactionAttributes);
        // Finish FactoryBean setup
        proxy.afterPropertiesSet();
        return type.cast(proxy.getObject());
    }

    public <T, E> E createTransactionalBean(T service, Class<E> type, Map<String, String> map) {
        TransactionProxyFactoryBean proxy = new TransactionProxyFactoryBean();
        // Inject transaction manager here
        proxy.setTransactionManager(transactionManager);
        // Define wich object instance is to be proxied (your bean)
        proxy.setTarget(service);
        // Programmatically setup transaction attributes
        Properties transactionAttributes = new Properties();
        transactionAttributes.putAll(map);
        proxy.setTransactionAttributes(transactionAttributes);
        // Finish FactoryBean setup
        proxy.afterPropertiesSet();
        return type.cast(proxy.getObject());
    }

}
