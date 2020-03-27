package com.pineframework.core.messaging.activemq.config;

import com.pineframework.core.datastructure.AbstractBusiness;
import com.pineframework.core.datastructure.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Queue;
import java.util.UUID;

import static io.vavr.control.Try.run;

@Component
public class MessagePublisher extends AbstractBusiness {

    private JmsTemplate jmsTemplate;

    private Queue messageQueue;

    public MessageModel publish(MessageModel model) {
        model.setCorrelationID(UUID.randomUUID().toString());
        jmsTemplate.convertAndSend(messageQueue, model, message -> createMessage(message, model));
        infoLog(model);
        return model;
    }

    public Message createMessage(Message message, MessageModel model) {
        run(() -> message.setJMSCorrelationID(model.getCorrelationID()));
        return message;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setMessageQueue(@Qualifier("MessageQueue") Queue messageQueue) {
        this.messageQueue = messageQueue;
    }

}