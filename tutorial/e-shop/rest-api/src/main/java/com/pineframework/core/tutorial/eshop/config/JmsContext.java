package com.pineframework.core.tutorial.eshop.config;

import com.pineframework.core.business.jms.JmsListener;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.spring.messaging.SpringQueueService;
import com.pineframework.core.tutorial.eshop.business.service.queue.MainQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.queue.StatusQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.queue.listener.MainQueueListener;
import com.pineframework.core.tutorial.eshop.business.service.queue.listener.StatusQueueListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.util.Map;

import static com.pineframework.core.spring.restapi.config.ApplicationContextBean.getBean;

@SuppressWarnings("rawtypes")
@Configuration
public class JmsContext {

    private final Map<String, Queue> queues;

    private final TransactionalBeanFactory txBeanFactory;

    public JmsContext(@Qualifier("queues") Map<String, Queue> queues, TransactionalBeanFactory txBeanFactory) {
        this.queues = queues;
        this.txBeanFactory = txBeanFactory;
    }

    @Bean(name = "mainQueueService")
    public QueueService mainQueueService(JmsTemplate jmsTemplate) {
        SpringQueueService queueService = new SpringQueueService(queues.get("sample-queue"), jmsTemplate);
        return txBeanFactory.create(new MainQueueProxyServiceImpl(queueService), QueueService.class);
    }

    @Bean(name = "statusQueueService")
    public QueueService statusQueueService(JmsTemplate jmsTemplate) {
        SpringQueueService queueService = new SpringQueueService(queues.get("status-queue"), jmsTemplate);
        return txBeanFactory.create(new StatusQueueProxyServiceImpl(queueService), QueueService.class);
    }

    @Profile(value = {"development", "uat", "production"})
    @Bean
    @DependsOn("statusQueueService")
    public JmsListener[] jmsListeners() {
        JmsListener[] listeners = new JmsListener[2];
        listeners[0] = new MainQueueListener(getBean("statusQueueService", QueueService.class));
        listeners[1] = new StatusQueueListener();

        return listeners;
    }

    @Profile("test")
    @Bean
    @DependsOn("statusQueueService")
    public JmsListener[] jmsListenersTest() {
        JmsListener[] listeners = new JmsListener[1];
        listeners[0] = new MainQueueListener(getBean("statusQueueService", QueueService.class));
        return listeners;
    }
}
