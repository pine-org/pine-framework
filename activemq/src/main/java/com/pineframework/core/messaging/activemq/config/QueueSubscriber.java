package com.pineframework.core.messaging.activemq.config;


import com.pineframework.core.datastructure.AbstractBusiness;
import com.pineframework.core.datastructure.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

import static com.pineframework.core.messaging.activemq.config.MessageSelectorGenerator.correlationIDSelectors;

@Component
public class QueueSubscriber extends AbstractBusiness {

    private JmsTemplate jmsTemplate;

    private Queue statusQueue;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setStatusQueue(@Qualifier("StatusQueue") Queue statusQueue) {
        this.statusQueue = statusQueue;
    }

    @JmsListener(destination = "${messaging.message-gueue.name}")
    public void subscribeMessage(MessageModel model, @Header(JmsHeaders.CORRELATION_ID) String correlationID) {
        infoLog(model);
        sendStatus("Accepted", correlationID);
    }

    public void sendStatus(String status, String correlationID) {
        jmsTemplate.convertAndSend(statusQueue, status, message -> {
            message.setJMSCorrelationID(correlationID);
            return message;
        });
    }

    public void readStatus(MessageModel model) {
        model.setStatus(getStatus(model.getCorrelationID()));
    }

    public String getStatus(String correlationID) {
        return (String) jmsTemplate.receiveSelectedAndConvert(statusQueue, correlationIDSelectors(correlationID));
    }

}