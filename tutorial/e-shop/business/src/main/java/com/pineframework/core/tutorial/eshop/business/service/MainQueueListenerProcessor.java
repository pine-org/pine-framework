package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.queue.QueueListenerProcessor;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.tutorial.eshop.model.MessageModel;

import java.util.Map;

public class MainQueueListenerProcessor implements QueueListenerProcessor<String, MessageModel> {

    @Override
    public void process(QueueService queueService, MessageModel model, Map<String, Object> metadata) {
        queueService.save(new MessageModel.Builder().id(model.getId()).content(MqStatus.ACCEPTED).build());

    }
}
