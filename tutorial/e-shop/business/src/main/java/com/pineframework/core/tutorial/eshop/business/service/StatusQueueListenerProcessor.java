package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.queue.QueueListenerProcessor;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.helper.LogUtils;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import org.slf4j.Logger;

import java.util.Map;

public class StatusQueueListenerProcessor implements QueueListenerProcessor<String, MessageModel> {

    private final Logger logger = LogUtils.getLogger(StatusQueueListenerProcessor.class);

    @Override
    public void process(MessageModel model, Map<String, Object> metadata) {
        logger.info(model.toString());
    }
}
