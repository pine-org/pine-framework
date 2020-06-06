package com.pineframework.core.jms;

import com.pineframework.core.business.helper.DefaultQueueIdGenerator;
import com.pineframework.core.contract.service.CorrelationIdGenerator;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;
import io.vavr.control.Try;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Optional;

import static com.pineframework.core.business.helper.QueueQueryUtils.correlationIdClause;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class SpringQueueService implements QueueService<String, MessageModel> {

    private JmsTemplate jmsTemplate;

    private Queue queue;

    private CorrelationIdGenerator idGenerator = new DefaultQueueIdGenerator();

    private MessageModel.Builder modelBuilder = new MessageModel.Builder();

    public SpringQueueService(Queue queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Optional<MessageModel> save(MessageModel m) {
        final MessageModel model = isNull(m.getId()) ? modelBuilder.from(m).id(idGenerator.next()).build() : m;
        jmsTemplate.send(queue, createMessage(model));
        return ofNullable(model);
    }

    public MessageCreator createMessage(MessageModel m) {
        return session -> {
            TextMessage message = session.createTextMessage();
            message.setText(m.toString());
            message.setJMSCorrelationID(String.valueOf(m.getId()));
            return message;
        };
    }

    @Override
    public Optional<MessageModel> findById(String id) {
        TextMessage textMessage = (TextMessage) jmsTemplate.receiveSelected(queue, correlationIdClause(id));
        String text = Try.of(() -> textMessage.getText()).get();
        return ofNullable(modelBuilder.fromJson(String.valueOf(text)).build());
    }

}
