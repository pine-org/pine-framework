package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;
import java.util.Map;

/**
 * Listen to specific queue.
 *
 * @param <I> identity
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@FunctionalInterface
public interface QueueListenerProcessor<I extends Serializable, M extends FlatTransient<I>> {

    void process(M model, Map<String, Object> metadata);
}
