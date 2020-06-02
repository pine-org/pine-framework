package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.business.helper.DefaultQueueIdGenerator;
import com.pineframework.core.business.repository.JpaRepositoryImpl;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.contract.service.queue.QueueListener;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.jms.SpringQueueListener;
import com.pineframework.core.jms.SpringQueueService;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepositoryImpl;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.MainQueueListenerProcessor;
import com.pineframework.core.tutorial.eshop.business.service.MainQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.MainQueueService;
import com.pineframework.core.tutorial.eshop.business.service.StatusQueueProxyService;
import com.pineframework.core.tutorial.eshop.business.service.StatusQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Queue;

@Configuration
@EntityScan("com.pineframework.core.tutorial.eshop.business.domain")
@ComponentScan(value = {"com.pineframework.core"})
@EnableTransactionManagement
public class BeanContext {

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
    public QueueIdGenerator queueIdGenerator() {
        return new DefaultQueueIdGenerator();
    }

    @Bean(name = "springQueueService")
    public QueueService springQueueService(@Qualifier("mainQueue") Queue queue,
                                           @Qualifier("defaultQueueIdGenerator") QueueIdGenerator idGenerator) {

        return new SpringQueueService(queue, idGenerator, new MessageModel.Builder());
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

    @Bean(name = "mainQueueService")
    public MainQueueService mainQueueService(@Qualifier("springQueueService") QueueService service) {
        return createTransactionalBean(new MainQueueProxyServiceImpl(service), MainQueueService.class);
    }

    @Bean(name = "statusQueueService")
    public StatusQueueProxyService statusQueueService(@Qualifier("springQueueService") QueueService service) {
        return createTransactionalBean(new StatusQueueProxyServiceImpl(service), StatusQueueProxyService.class);
    }

    @Bean(name = "mainQueueListener")
    public QueueListener mainQueueListener(@Qualifier("statusQueueService") QueueService queueService) {
        return createTransactionalBean(new SpringQueueListener(queueService, new MainQueueListenerProcessor()),
                QueueListener.class);
    }

}
