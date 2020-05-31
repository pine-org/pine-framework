package com.pineframework.core.contract.service.queue;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;

public interface QueueListener<I extends Serializable, M extends FlatTransient<I>> {
    void listener(M model, I correlationID);
}
