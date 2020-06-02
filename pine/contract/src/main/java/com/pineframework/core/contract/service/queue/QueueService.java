package com.pineframework.core.contract.service.queue;

import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.contract.service.entityservice.CrudService;
import com.pineframework.core.contract.service.entityservice.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;

import javax.jms.Queue;
import java.io.Serializable;

public interface QueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> extends CrudService<I, M>, QueryService<I, M> {

    Queue getQueue();

    QueueIdGenerator getIdGenerator();

    B getModelBuilder();

}
