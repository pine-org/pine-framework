package com.pineframework.core.tutorial.eshop.integrationtest;

import com.pineframework.core.business.service.queue.AbstractQueueService;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.test.AbstractTest;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("E-Shop Test ")
@SpringBootTest
public class SpringJmsApplicationTest extends AbstractTest {

    @Autowired
    @Qualifier("mainQueueService")
    private QueueService<String, SampleModel, SampleModel.Builder> mainQueue;

    @Autowired
    @Qualifier("statusQueueService")
    private QueueService<String, SampleModel, SampleModel.Builder> statusQueue;

    @DisplayName("Send message to main queue get ACCEPTED status from status queue")
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void save_SendMessagesToMainQueue_GetAcceptedStatusFromStatusQueue(String text) {
        Optional<SampleModel> message = mainQueue.save(new SampleModel.Builder().content(text).build());
        Optional<SampleModel> status = statusQueue.findById(message.get().getId());
        logInfo(message);
        assertThat(MqStatus.valueOf(String.valueOf(status.get().getContent()))).isEqualTo(MqStatus.ACCEPTED);
    }
}