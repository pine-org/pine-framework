package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.test.AbstractTest;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.pineframework.core.tutorial.eshop.business.helper.StatusHelper.toEnum;

@DisplayName("E-Shop Message Queue Tests")
@SpringBootTest
public class MessageQueueTest extends AbstractTest {

    @Autowired
    @Qualifier("mainQueueService")
    private QueueService<String, MessageModel, MessageModel.Builder> mainQueue;

    @Autowired
    @Qualifier("statusQueueService")
    private QueueService<String, MessageModel, MessageModel.Builder> statusQueue;

    @DisplayName("Send message to main queue and get accepted status from status queue")
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void save_SendMessagesToMainQueue_GetAcceptedStatusFromStatusQueue(String text) {
        Optional<MessageModel> message = mainQueue.save(new MessageModel.Builder().content(text).build());
        Optional<MessageModel> status = statusQueue.findById(message.get().getId());
        logInfo(message.get());
        Assertions.assertEquals(MqStatus.ACCEPTED, toEnum(status.get()));
    }

}