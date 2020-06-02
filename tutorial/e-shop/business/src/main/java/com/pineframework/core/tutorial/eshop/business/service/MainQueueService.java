package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;

public interface MainQueueService extends QueueService<String, MessageModel, MessageModel.Builder> {
}
