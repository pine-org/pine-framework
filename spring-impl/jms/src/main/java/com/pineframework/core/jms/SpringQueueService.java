package com.pineframework.core.jms;

import com.pineframework.core.contract.service.QueueIdGenerator;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.Optional;

import static com.pineframework.core.business.helper.QueueQueryUtils.correlationIdClause;
import static io.vavr.control.Try.run;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class SpringQueueService<I extends Serializable,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> implements QueueService<I, M, B> {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Queue queue;

    private QueueIdGenerator idGenerator;

    private B modelBuilder;

    public SpringQueueService(Queue queue, QueueIdGenerator idGenerator, B modelBuilder) {
        this.queue = queue;
        this.idGenerator = idGenerator;
        this.modelBuilder = modelBuilder;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Override
    public Queue getQueue() {
        return queue;
    }

    @Override
    public QueueIdGenerator getIdGenerator() {
        return idGenerator;
    }

    @Override
    public B getModelBuilder() {
        return modelBuilder;
    }

    @Override
    public Optional<I> save(M m) {
        final M model = isNull(m.getId()) ? getModelBuilder().from(m).id((I) getIdGenerator().next()).build() : m;
        getJmsTemplate().convertAndSend(getQueue(), model, message -> writeMetaData(message, model));
        return ofNullable(model.getId());
    }

    @Override
    public Optional<M> findById(I id) {
        Object o = getJmsTemplate().receiveSelectedAndConvert(getQueue(), correlationIdClause(id));
        return ofNullable((M) o);
    }

    @Override
    public void update(M m) {

    }

    @Override
    public void delete(I id) {

    }

    @Override
    public M[] findAll() {
        return null;
    }

    @Override
    public Page findByPage(Page page) {
        return null;
    }

    @Override
    public long count(Filter... filters) {
        return 0;
    }

    @Override
    public M[] findByFilter(Filter... filters) {
        return null;
    }

    @Override
    public Optional<M> findByModel(M m) {
        return Optional.empty();
    }

    protected Message writeMetaData(Message message, M model) {
        run(() -> message.setJMSCorrelationID((String) model.getId()));
        return message;
    }
}
