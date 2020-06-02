package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;
import java.util.Map;

@FunctionalInterface
public interface QueueListenerProcessor<I extends Serializable, M extends FlatTransient<I>> {

    void process(M model, Map<String, Object> metadata);
}
