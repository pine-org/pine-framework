package com.pineframework.core.tutorial.eshop.business.service.queue.listener;

import com.pineframework.core.business.jms.JmsListenertHolder;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel.Builder;
import io.vavr.control.Try;

import javax.jms.Message;
import javax.jms.MessageListener;

import static com.pineframework.core.datamodel.model.message.MqStatus.ACCEPTED;

public class MainQueueListenerHolder implements JmsListenertHolder {

    private QueueService queueService;

    public MainQueueListenerHolder(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public String getId() {
        return "mainQueueListener";
    }

    @Override
    public String getDestination() {
        return "sample-queue";
    }

    @Override
    public MessageListener getListener() {
        return message -> queueService.save(new Builder().id(getJmsCorrelationID(message)).content(ACCEPTED).build());
    }

    public String getJmsCorrelationID(Message message) {
        return Try.of(() -> message.getJMSCorrelationID()).get();
    }

}
