package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;
import java.util.Optional;

/**
 * Provide push operation in order to push a message to a queue.
 *
 * @param <I> identity
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface QueueService<I extends Serializable, M extends FlatTransient<I>> {

    /**
     * Push message to a queue.
     *
     * @param m transient object
     * @return transient object include correlation identity
     */
    Optional<M> push(M m);

    Optional<M> findByCorrelationId(I id);
}
