package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;
import java.util.Optional;

public interface QueueService<I extends Serializable, M extends FlatTransient<I>> {

    Optional<M> push(M m);

    Optional<M> findByCorrelationId(I id);
}
