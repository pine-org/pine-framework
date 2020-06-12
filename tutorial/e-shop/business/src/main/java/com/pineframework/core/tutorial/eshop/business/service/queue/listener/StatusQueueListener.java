package com.pineframework.core.tutorial.eshop.business.service.queue.listener;

import com.pineframework.core.business.jms.JmsListener;
import io.vavr.control.Try;

import javax.jms.MessageListener;

public class StatusQueueListener implements JmsListener {

    @Override
    public String getId() {
        return "statusQueueListener";
    }

    @Override
    public String getDestination() {
        return "status-queue";
    }

    @Override
    public MessageListener getProcess() {
        return message -> System.out.println("Status Queue : " + Try.of(() -> message.getJMSCorrelationID()).get());
    }
}
