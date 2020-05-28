package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.queue.AbstractMainQueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import org.springframework.stereotype.Service;

@Service("mainQueueService")
public class MainQueueServiceImpl extends AbstractMainQueueService<String, MessageModel, MessageModel.Builder> {

    @Override
    public MessageModel.Builder getModelBuilder() {
        return new MessageModel.Builder();
    }
}
