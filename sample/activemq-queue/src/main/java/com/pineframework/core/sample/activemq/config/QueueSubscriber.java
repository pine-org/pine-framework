package com.pineframework.core.sample.activemq.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.pineframework.core.sample.activemq.config.MessagePublisher.sendStatus;

@Component
public class QueueSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueSubscriber.class);

    @JmsListener(destination = "${messaging.message-gueue.name}")
    public void subscribeMessage(String message, @Header(JmsHeaders.CORRELATION_ID) String correlationID) {
        LOGGER.info("subscribed: '{}', '{}'", message, correlationID);
        sendStatus("OK", correlationID);
    }

}