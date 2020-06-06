package com.pineframework.core.tutorial.eshop;

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
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.pineframework.core.tutorial.eshop.business.domain")
@ComponentScan(value = {"com.pineframework.core"})
@EnableTransactionManagement
public class ServiceBeanContext {

    @Autowired
    private TransactionalBeanFactory transactionalBeanFactory;

    private <T, E> E createTransactionalBean(T service, Class<E> type) {
        return Try.of(() -> transactionalBeanFactory.createTransactionalBean(service, type)).get();
    }

    @Bean(name = "jpaRepository")
    public JpaRepository jpaRepository() {
        return new JpaRepositoryImpl();
    }

    @Bean(name = "defaultQueueIdGenerator")
    public CorrelationIdGenerator queueIdGenerator() {
        return new DefaultQueueIdGenerator();
    }

    @Bean(name = "goodsRepository")
    public GoodsRepository goodsRepository(JpaRepository jpaRepository) {
        return new GoodsRepositoryImpl(jpaRepository);
    }

    @Bean(name = "goodsTransformer")
    public GoodsTransformer goodsTransformer() {
        return new GoodsTransformer();
    }

    @Bean(name = "goodsEntityService")
    public GoodsEntityService goodsEntityService(GoodsRepository repository, GoodsTransformer transformer) {
        return createTransactionalBean(new GoodsEntityServiceImpl(repository, transformer), GoodsEntityService.class);
    }

}
