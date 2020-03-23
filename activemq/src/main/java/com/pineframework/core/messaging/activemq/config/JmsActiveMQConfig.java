package com.pineframework.core.messaging.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@Configuration
@EnableJms
@RefreshScope
public class JmsActiveMQConfig {

    @Value("${messaging.message-gueue.name}")
    private String messageQueue;

    @Value("${messaging.status-gueue.name}")
    private String statusQueue;

    @Bean("MessageQueue")
    public Queue messageQueue() {
        return new ActiveMQQueue(messageQueue);
    }

    @Bean("StatusQueue")
    public Queue statusQueue() {
        return new ActiveMQQueue(statusQueue);
    }
}