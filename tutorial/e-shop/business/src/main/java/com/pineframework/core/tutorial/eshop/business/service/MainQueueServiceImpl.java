package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.AbstractProxyQueueService;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;

public class MainQueueServiceImpl extends AbstractProxyQueueService<String, MessageModel, MessageModel.Builder> {

    public MainQueueServiceImpl(QueueService<String, MessageModel, MessageModel.Builder> service) {
        super(service);
    }
}
