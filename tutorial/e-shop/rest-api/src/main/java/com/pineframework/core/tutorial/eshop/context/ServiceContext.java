package com.pineframework.core.tutorial.eshop.context;

import com.pineframework.core.business.helper.DefaultQueueIdGenerator;
import com.pineframework.core.business.repository.JpaRepositoryImpl;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.contract.service.CorrelationIdGenerator;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepositoryImpl;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityServiceImpl;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceContext {

    @Autowired
    private TransactionalBeanFactory txBeanFactory;

    @Bean(name = "jpaRepository")
    public JpaRepository jpaRepository() {
        return new JpaRepositoryImpl();
    }

    @Bean(name = "goodsRepository")
    public GoodsRepository goodsRepository(JpaRepository jpaRepository) {
        return new GoodsRepositoryImpl(jpaRepository);
    }

    @Bean(name = "goodsEntityService")
    public GoodsEntityService goodsEntityService(GoodsRepository repository) {
        return txBeanFactory.create(new GoodsEntityServiceImpl(repository, new GoodsTransformer()),
                GoodsEntityService.class);
    }

}
