package com.pineframework.core.business.service;

import com.pineframework.core.datamodel.model.FlatTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.Queue;
import java.io.Serializable;

/**
 * @author Saman Alishirishahrbabak
 */
public abstract class StatusQueueService<I extends Serializable, M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>>
        extends QueueService<I, M, B> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("statusQueue")
    private Queue queue;

    public Queue getQueue() {
        return queue;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}