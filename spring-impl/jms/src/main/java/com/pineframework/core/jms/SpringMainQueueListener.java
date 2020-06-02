package com.pineframework.core.jms;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.service.queue.QueueListener;
import com.pineframework.core.contract.service.queue.QueueListenerProcessor;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Saman Alishirishahrbabak
 */
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpringMainQueueListener<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>>
        implements Loggable, QueueListener<I, M> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private QueueListenerProcessor<I, M> processor;

    public SpringMainQueueListener(QueueListenerProcessor<I, M> processor) {
        this.processor = processor;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @JmsListener(destination = "${messaging.main-queue.name}")
    @Override
    public void listener(M model, @Header(JmsHeaders.CORRELATION_ID) I correlationID) {
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put(JmsHeaders.CORRELATION_ID, correlationID);

        processor.process(model, metadata);
    }

}