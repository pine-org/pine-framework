package com.pineframework.core.tutorial.eshop.config;

import com.pineframework.core.business.repository.JpaRepositoryImpl;
import com.pineframework.core.cache.javacache.JvmHashTemplate;
import com.pineframework.core.cache.oracle.OracleChangeDataCapture;
import com.pineframework.core.cache.oracle.OracleChangeDataCaptureToJvmCache;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepository;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepositoryImpl;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityService;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityServiceImpl;
import com.pineframework.core.tutorial.eshop.business.transformer.ProductTransformer;
import io.vavr.control.Try;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class ServiceContext {

    @Autowired
    private TransactionalBeanFactory txBeanFactory;

    public OracleConnection getOracleConnection() throws SQLException {
        Properties usernameAndPassword = new Properties();
        usernameAndPassword.setProperty("user", "pine");
        usernameAndPassword.setProperty("password", "pine");

        OracleDriver driver = new OracleDriver();
        return (OracleConnection) driver.connect("jdbc:oracle:thin:@localhost:1521:pine", usernameAndPassword);
    }

    @Bean(name = "jpaRepository")
    public Repository jpaRepository() {
        return new JpaRepositoryImpl();
    }

    @Bean(name = "productRepository")
    public ProductRepository productRepository(Repository repository) {
        return new ProductRepositoryImpl(repository);
    }

    @Bean(name = "productEntityService")
    public ProductEntityService productEntityService(ProductRepository repository) {
        return txBeanFactory.create(new ProductEntityServiceImpl(repository, new ProductTransformer()),
                ProductEntityService.class);
    }

    @Bean(name = "jvmCache", destroyMethod = "")
    public OracleChangeDataCapture jvmCache() {
        OracleChangeDataCaptureToJvmCache bean = new OracleChangeDataCaptureToJvmCache(
                Try.of(() -> getOracleConnection()).get(),
                new JvmHashTemplate(),
                "com.pineframework.core.tutorial.eshop.model"
        );
        bean.init();
        return bean;
    }

}
