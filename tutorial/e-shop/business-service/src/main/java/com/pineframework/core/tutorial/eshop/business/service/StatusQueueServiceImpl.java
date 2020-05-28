package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.queue.StatusQueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import org.springframework.stereotype.Service;

@Service("statusQueueService")
public class StatusQueueServiceImpl extends StatusQueueService<String, MessageModel, MessageModel.Builder> {

    @Override
    public MessageModel.Builder getModelBuilder() {
        return new MessageModel.Builder();
    }
}
