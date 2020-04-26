package com.pineframework.core.messaging.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Queue;

/**
 * Configuration for connect to ActiveMQ Classic. Create bean from {@link ActiveMqConfigurationServerProfile}
 * when {@code config-server} profile was added to application properties.
 * {@link ActiveMqConfigurationServerProfile} support json type.
 *
 * @author Saman Alishirishahrbabak
 */
@Configuration
@EnableJms
@RefreshScope
@Profile("config-server")
public class ActiveMqConfigurationServerProfile {

    @Value("${messaging.message-gueue.name}")
    private String messageQueue;

    @Value("${messaging.status-gueue.name}")
    private String statusQueue;

    @Bean("messageQueue")
    public Queue messageQueue() {
        return new ActiveMQQueue(messageQueue);
    }

    @Bean("statusQueue")
    public Queue statusQueue() {
        return new ActiveMQQueue(statusQueue);
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}