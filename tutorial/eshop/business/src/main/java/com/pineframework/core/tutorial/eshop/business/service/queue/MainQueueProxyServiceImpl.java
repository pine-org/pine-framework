package com.pineframework.core.tutorial.eshop.business.service.queue;

import com.pineframework.core.business.service.AbstractProxyQueueService;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;

public class MainQueueProxyServiceImpl extends AbstractProxyQueueService {

    public MainQueueProxyServiceImpl(QueueService<String, MessageModel> service) {
        super(service);
    }
}
