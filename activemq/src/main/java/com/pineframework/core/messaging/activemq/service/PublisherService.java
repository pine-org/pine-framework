package com.pineframework.core.messaging.activemq.service;

import com.pineframework.core.datastructure.model.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Queue;
import java.util.UUID;

import static io.vavr.control.Try.run;

@Component
public class PublisherService implements Loggable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("messageQueue")
    private Queue messageQueue;

    @Override
    public Logger getLogger() {
        return logger;
    }

    public MessageModel publish(MessageModel model) {
        model.setCorrelationId(UUID.randomUUID().toString());
        jmsTemplate.convertAndSend(messageQueue, model, message -> writeToModel(message, model));
        infoLog(model);
        return model;
    }

    public Message writeToModel(Message message, MessageModel model) {
        run(() -> message.setJMSCorrelationID(model.getCorrelationId()));
        return message;
    }
}