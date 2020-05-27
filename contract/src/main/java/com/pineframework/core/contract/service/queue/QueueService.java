package com.pineframework.core.contract.service.queue;

import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.io.Serializable;
import java.util.Optional;

public interface QueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> {

    Queue getQueue();

    JmsTemplate getJmsTemplate();

    QueueIdGenerator getIdGenerator();

    B getModelBuilder();

    Optional<M> save(M m);

    Optional<M> findById(I id);
}
