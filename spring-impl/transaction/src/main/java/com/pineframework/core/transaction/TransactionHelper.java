package com.pineframework.core.transaction;

import com.pineframework.core.contract.config.TransactionaBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import java.util.Properties;

@Component
public class TransactionHelper implements TransactionaBeanFactory {

    @Autowired
    private PlatformTransactionManager transactionManager;

    public <T, E> E createTransactionalBean(T service, Class<E> type) throws Throwable {
        TransactionProxyFactoryBean proxy = new TransactionProxyFactoryBean();
        // Inject transaction manager here
        proxy.setTransactionManager(transactionManager);
        // Define wich object instance is to be proxied (your bean)
        proxy.setTarget(service);
        // Programmatically setup transaction attributes
        Properties transactionAttributes = new Properties();
        transactionAttributes.put("*", "PROPAGATION_REQUIRED");
        proxy.setTransactionAttributes(transactionAttributes);
        // Finish FactoryBean setup
        proxy.afterPropertiesSet();
        return type.cast(proxy.getObject());
    }

}
