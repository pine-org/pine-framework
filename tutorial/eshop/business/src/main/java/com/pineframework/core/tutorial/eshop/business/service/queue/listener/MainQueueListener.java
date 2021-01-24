package com.pineframework.core.tutorial.eshop.business.service.queue.listener;

import com.pineframework.core.business.jms.JmsListener;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel.Builder;
import io.vavr.control.Try;

import javax.jms.Message;
import javax.jms.MessageListener;

import static com.pineframework.core.datamodel.model.message.MqStatus.ACCEPTED;

public class MainQueueListener implements JmsListener {

    private final QueueService statusQueueService;

    public MainQueueListener(QueueService statusQueueService) {
        this.statusQueueService = statusQueueService;
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
    public MessageListener getProcess() {
        return message -> statusQueueService.push(new Builder()
                .id(getJmsCorrelationID(message))
                .content(ACCEPTED)
                .build());
    }

    public String getJmsCorrelationID(Message message) {
        return Try.of(() -> message.getJMSCorrelationID()).get();
    }

}
