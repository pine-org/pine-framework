package com.pineframework.core.messaging.activemq.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.datastructure.model.messaging.MessageModel;
import com.pineframework.core.datastructure.model.messaging.MqStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

import static com.pineframework.core.messaging.activemq.utils.MessageSelectorGenerator.correlationIdSelectors;

/**
 * @author Saman Alishirishahrbabak
 */
@Component
public class SubscriberService implements Loggable {

    public static final String ACCEPTED = "Accepted";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("statusQueue")
    private Queue statusQueue;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @JmsListener(destination = "${messaging.message-queue.name}")
    public void subscribe(MessageModel model, @Header(JmsHeaders.CORRELATION_ID) String correlationID) {
        infoLog(model);
        sendStatus(MqStatus.ACCEPTED, correlationID);
    }

    public void sendStatus(MqStatus status, String correlationId) {
        jmsTemplate.convertAndSend(statusQueue, status.getValue(), message -> {
            message.setJMSCorrelationID(correlationId);
            return message;
        });
    }

    public void writeStatus(MessageModel model) {
        model.setStatus(getStatus(model.getCorrelationId()));
    }

    public MqStatus getStatus(String correlationId) {
        String s = (String) jmsTemplate.receiveSelectedAndConvert(statusQueue, correlationIdSelectors(correlationId));
        return MqStatus.instanceOf(s);
    }
}