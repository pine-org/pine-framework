package com.pineframework.core.messaging.activemq.service;

import com.pineframework.core.datastructure.model.message.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Saman Alishirishahrbabak
 */
@Component
public class QueueServiceProxy {

    private PublisherService publisher;

    private SubscriberService subscriber;

    public MessageModel publish(MessageModel model) {
        return publisher.publish(model);
    }

    public void writeStatus(MessageModel model) {
        subscriber.writeStatus(model);
    }

    @Autowired
    public void setPublisher(PublisherService publisher) {
        this.publisher = publisher;
    }

    @Autowired
    public void setSubscriber(SubscriberService subscriber) {
        this.subscriber = subscriber;
    }
}
