package com.pineframework.core.business.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.service.QueueIdentityGenerator;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.Optional;

import static com.pineframework.core.business.helper.QueueQueryUtils.correlationIdClause;
import static io.vavr.control.Try.run;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Transactional
public abstract class QueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> implements Loggable {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private QueueIdentityGenerator identityGenerator;

    protected abstract Queue getQueue();

    protected abstract B getBuilder();

    public QueueIdentityGenerator getIdentityGenerator() {
        return identityGenerator;
    }

    protected Message writeMetaData(Message message, M model) {
        run(() -> message.setJMSCorrelationID((String) model.getId()));
        return message;
    }

    public Optional<M> save(M m) {
        final M model = isNull(m.getId()) ? getBuilder().from(m).id((I) getIdentityGenerator().next()).build() : m;
        jmsTemplate.convertAndSend(getQueue(), model, message -> writeMetaData(message, model));
        return ofNullable(model);
    }

    public Optional<M> findById(I id) {
        Object o = jmsTemplate.receiveSelectedAndConvert(getQueue(), correlationIdClause(id));
        return ofNullable((M) o);
    }
}
