package com.pineframework.core.tutorial.eshop.business.service.queue.listener;

import com.pineframework.core.business.jms.JmsListenertHolder;
import io.vavr.control.Try;

import javax.jms.MessageListener;

public class StatusQueueListenerHolder implements JmsListenertHolder {

    @Override
    public String getId() {
        return "statusQueueListener";
    }

    @Override
    public String getDestination() {
        return "status-queue";
    }

    @Override
    public MessageListener getListener() {
        return message -> System.out.println("Status Queue : " + Try.of(() -> message.getJMSCorrelationID()).get());
    }
}
