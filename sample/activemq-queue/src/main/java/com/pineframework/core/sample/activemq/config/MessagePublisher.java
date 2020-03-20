package com.pineframework.core.sample.activemq.config;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

@Component
public class MessagePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisher.class);

    private static JmsTemplate jmsTemplate;
    private static Queue messageQueue;
    private static Queue statusQueue;


    public static String publish(String text) {
        AtomicReference<Message> messageHolder = new AtomicReference<>();

        jmsTemplate.convertAndSend(messageQueue, text, message -> {
            String correlationID = UUID.randomUUID().toString();
            LOGGER.info("published: '{}', '{}'", text, correlationID);
            message.setJMSCorrelationID(correlationID);
            messageHolder.set(message);
            return message;
        });

        return Try.of(() -> messageHolder.get().getJMSCorrelationID()).get();
    }

    public static void sendStatus(String status, String correlationID) {
        jmsTemplate.convertAndSend(statusQueue, status, message -> {
            message.setJMSCorrelationID(correlationID);
            return message;
        });
    }

    public static String getStatus(String correlationID) {
        return (String) jmsTemplate.receiveSelectedAndConvert(statusQueue, format("JMSCorrelationID = '%s'", correlationID));
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        MessagePublisher.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setMessageQueue(@Qualifier("MessageQueue") Queue messageQueue) {
        MessagePublisher.messageQueue = messageQueue;
    }

    @Autowired
    public void setStatusQueue(@Qualifier("StatusQueue") Queue statusQueue) {
        MessagePublisher.statusQueue = statusQueue;
    }
}