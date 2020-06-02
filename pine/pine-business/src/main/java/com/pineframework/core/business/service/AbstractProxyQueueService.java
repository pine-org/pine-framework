package com.pineframework.core.business.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Queue;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author Saman Alishirishahrbabak
 */
public abstract class AbstractProxyQueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> implements QueueService<I, M, B>, Loggable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private QueueService<I, M, B> service;

    public AbstractProxyQueueService(QueueService<I, M, B> service) {
        this.service = service;
    }

    @Override
    public Queue getQueue() {
        return service.getQueue();
    }

    @Override
    public QueueIdGenerator getIdGenerator() {
        return service.getIdGenerator();
    }

    @Override
    public B getModelBuilder() {
        return service.getModelBuilder();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Optional<I> save(M m) {
        return service.save(m);
    }

    @Override
    public Optional<M> findById(I id) {
        return service.findById(id);
    }

    @Override
    public void update(M m) {

    }

    @Override
    public void delete(I id) {

    }

    @Override
    public M[] findAll() {
        return null;
    }

    @Override
    public Page findByPage(Page page) {
        return null;
    }

    @Override
    public long count(Filter... filters) {
        return 0;
    }

    @Override
    public M[] findByFilter(Filter... filters) {
        return null;
    }

    @Override
    public Optional<M> findByModel(M m) {
        return Optional.empty();
    }
}