package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.pineframework.core.tutorial.eshop.business.helper.StatusHelper.toEnum;

@DisplayName("E-Shop Message Queue Tests")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MessageQueueTest extends AbstractTest {

    @Autowired
    @Qualifier("mainQueueService")
    private QueueService mainQueue;

    @Autowired
    @Qualifier("statusQueueService")
    private QueueService statusQueue;

    @DisplayName("send message to main queue and get accepted status from status queue")
    @ParameterizedTest(name = "{index} => message=''{0}''")
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void push_GivenMessage_WhenSendToMainQueue_ThenGetAcceptedStatusFromStatusQueue(String text) {
        Optional<MessageModel> message = mainQueue.push(new MessageModel.Builder().content(text).build());
        Optional<MessageModel> status = statusQueue.findByCorrelationId(message.get().getId());
        logInfo(message.get());
        Assertions.assertEquals(MqStatus.ACCEPTED, toEnum(status.get()));
    }

}