package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.AbstractProxyQueueService;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;

public class StatusQueueProxyServiceImpl extends AbstractProxyQueueService<String, MessageModel, MessageModel.Builder>
        implements StatusQueueProxyService {

    public StatusQueueProxyServiceImpl(QueueService<String, MessageModel, MessageModel.Builder> queueService) {
        super(queueService);
    }
}
