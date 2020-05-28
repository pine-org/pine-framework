package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.Serializable;
import java.util.Map;

public interface QueueListener<I extends Serializable, M extends FlatTransient<I>> {

    void listener(M model, @Header(JmsHeaders.CORRELATION_ID) I correlationID);

    void process(M model, Map<String, Object> metadata);

}
