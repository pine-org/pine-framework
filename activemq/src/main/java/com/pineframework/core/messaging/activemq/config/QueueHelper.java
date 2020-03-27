package com.pineframework.core.messaging.activemq.config;

import com.pineframework.core.datastructure.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueHelper {

    private MessagePublisher publisher;

    private QueueSubscriber subscriber;

    public MessageModel publish(MessageModel model) {
        return publisher.publish(model);
    }

    public void readStatus(MessageModel model) {
        subscriber.readStatus(model);
    }

    @Autowired
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    public void setSubscriber(QueueSubscriber subscriber) {
        this.subscriber = subscriber;
    }
}
