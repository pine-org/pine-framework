package com.pineframework.core.spring.activemq.config;

import com.pineframework.core.contract.config.QueueConfiguration;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.jms.Queue;
import java.util.Map;

import static com.pineframework.core.helper.CollectionUtils.mapOf;

/**
 * Configuration for connect to ActiveMQ Classic. Create bean from {@link ActiveMqDynamicConfiguration}
 * when {@code config-server} profile was added to application properties.
 * {@link ActiveMqDynamicConfiguration} support json type.
 *
 * @author Saman Alishirishahrbabak
 */
@Configuration
@RefreshScope
@Profile(value = {"config-server & activemq"})
public class ActiveMqDynamicConfiguration implements QueueConfiguration {

    @Value("${ACTIVEMQ_QUEUES:pine}")
    private String[] queues;

    @Bean("queues")
    public Map<String, Queue> createQueue() {
        return mapOf(queues, s -> s, s -> new ActiveMQQueue(s));
    }
}