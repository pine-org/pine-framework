package com.pineframework.core.business.service.queue;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import java.io.Serializable;
import java.util.Optional;

import static com.pineframework.core.business.helper.QueueQueryUtils.correlationIdClause;
import static io.vavr.control.Try.run;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Transactional
public abstract class AbstractQueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> implements Loggable, QueueService<I, M, B> {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private QueueIdGenerator identityGenerator;

    @Override
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Override
    public QueueIdGenerator getIdGenerator() {
        return identityGenerator;
    }

    @Override
    public Optional<M> save(M m) {
        final M model = isNull(m.getId()) ? getModelBuilder().from(m).id((I) getIdGenerator().next()).build() : m;
        getJmsTemplate().convertAndSend(getQueue(), model, message -> writeMetaData(message, model));
        return ofNullable(model);
    }

    @Override
    public Optional<M> findById(I id) {
        Object o = getJmsTemplate().receiveSelectedAndConvert(getQueue(), correlationIdClause(id));
        return ofNullable((M) o);
    }

    protected Message writeMetaData(Message message, M model) {
        run(() -> message.setJMSCorrelationID((String) model.getId()));
        return message;
    }
}
