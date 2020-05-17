package com.pineframework.core.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.model.message.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static com.pineframework.core.business.utils.QueueQueryUtils.correlationIdClause;
import static io.vavr.control.Try.run;
import static java.util.Optional.ofNullable;

public abstract class QueueService<I extends Serializable, M extends FlatTransient<I>> implements Loggable {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JmsTemplate jmsTemplate;

    protected abstract Queue getQueue();

    protected abstract Class<M> getTransientType();

    protected Message writeMetaData(Message message, M model) {
        run(() -> message.setJMSCorrelationID((String) model.getId()));
        return message;
    }

    public I save(M model) {
        if (model.getId() == null)
            model.setId((I) UUID.randomUUID().toString());

        jmsTemplate.convertAndSend(getQueue(), model, message -> writeMetaData(message, model));
        return model.getId();
    }

    public Optional<M> findById(I id) {
        Object o = jmsTemplate.receiveSelectedAndConvert(getQueue(), correlationIdClause(id));
        return (Optional<M>) ofNullable((MessageModel) o);
    }
}
