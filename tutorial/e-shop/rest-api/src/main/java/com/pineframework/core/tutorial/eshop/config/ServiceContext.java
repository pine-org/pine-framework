package com.pineframework.core.tutorial.eshop.config;

import com.pineframework.core.business.repository.JpaRepositoryImpl;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepository;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepositoryImpl;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityService;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityServiceImpl;
import com.pineframework.core.tutorial.eshop.business.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceContext {

    @Autowired
    private TransactionalBeanFactory txBeanFactory;

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

}
