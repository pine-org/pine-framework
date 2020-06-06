package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.business.jms.JmsListenertHolder;
import com.pineframework.core.contract.config.TransactionalBeanFactory;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.jms.JmsListenerRegistrar;
import com.pineframework.core.jms.SpringQueueService;
import com.pineframework.core.tutorial.eshop.business.service.queue.MainQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.queue.StatusQueueProxyServiceImpl;
import com.pineframework.core.tutorial.eshop.business.service.queue.listener.MainQueueListenerHolder;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.util.Map;

import static com.pineframework.core.tutorial.eshop.ApplicationContextBean.getBean;

@Configuration
public class JmsBeanContext extends JmsListenerRegistrar {

    @Autowired
    @Qualifier("queues")
    private Map<String, Queue> queues;

    @Autowired
    private TransactionalBeanFactory transactionalBeanFactory;

    private <T, E> E createTransactionalBean(T service, Class<E> type) {
        return Try.of(() -> transactionalBeanFactory.createTransactionalBean(service, type)).get();
    }

    @Bean(name = "mainQueueService")
    public QueueService mainQueueService(JmsTemplate jmsTemplate) {
        SpringQueueService queueService = new SpringQueueService(queues.get("sample-queue"), jmsTemplate);
        return createTransactionalBean(new MainQueueProxyServiceImpl(queueService), QueueService.class);
    }

    @Bean(name = "statusQueueService")
    public QueueService statusQueueService(JmsTemplate jmsTemplate) {
        SpringQueueService queueService = new SpringQueueService(queues.get("status-queue"), jmsTemplate);
        return createTransactionalBean(new StatusQueueProxyServiceImpl(queueService), QueueService.class);
    }

    @Override
    public JmsListenertHolder[] getEndpoint() {
        JmsListenertHolder[] holders = new JmsListenertHolder[1];
        holders[0] = new MainQueueListenerHolder(getBean("statusQueueService", QueueService.class));
        //holders[1] = new StatusQueueListenerHolder();

        return holders;
    }
}
