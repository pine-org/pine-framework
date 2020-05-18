package com.pineframework.core.business.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Saman Alishirishahrbabak
 */
@Transactional
public abstract class QueueListener<I extends Serializable, M extends FlatTransient<I>>
        implements Loggable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Logger getLogger() {
        return logger;
    }

    @JmsListener(destination = "${messaging.main-queue.name}")
    public void listener(M model, @Header(JmsHeaders.CORRELATION_ID) I correlationID) {
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put(JmsHeaders.CORRELATION_ID, correlationID);

        process(model, metadata);
    }

    protected abstract void process(M model, Map<String, Object> metadata);

}