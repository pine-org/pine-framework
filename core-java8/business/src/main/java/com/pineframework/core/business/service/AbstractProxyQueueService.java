package com.pineframework.core.business.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractProxyQueueService implements QueueService<String, MessageModel>, Loggable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QueueService<String, MessageModel> service;

    public AbstractProxyQueueService(QueueService<String, MessageModel> service) {
        this.service = service;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Optional<MessageModel> push(MessageModel m) {
        beforeSave(m);
        Optional<MessageModel> model = service.push(m);
        afterSave(m);
        return model;
    }

    @Override
    public Optional<MessageModel> findByCorrelationId(String id) {
        return service.findByCorrelationId(id);
    }

    protected void afterSave(MessageModel m) {

    }

    protected void beforeSave(MessageModel m) {

    }
}