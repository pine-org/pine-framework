package com.pineframework.core.tutorial.activemqsample;

import com.pineframework.core.business.service.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.test.AbstractTest;
import com.pineframework.core.tutorial.activemqsample.model.SampleModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ActiveMQ Sample Test ")
@SpringBootTest
public class SpringJmsApplicationTest extends AbstractTest {

    @Autowired
    @Qualifier("sampleMainQueueService")
    private QueueService<String, SampleModel> mainQueue;

    @Autowired
    @Qualifier("sampleStatusQueueService")
    private QueueService<String, SampleModel> statusQueue;

    @DisplayName("Send message to main queue get ACCEPTED status from status queue")
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void save_SendMessagesToMainQueue_GetAcceptedStatusFromStatusQueue(String text) {
        SampleModel model = new SampleModel();
        model.setContent(text);

        String id = mainQueue.save(model);

        Optional<SampleModel> status = statusQueue.findById(id);
        logInfo(model);

        assertThat(MqStatus.valueOf(String.valueOf(status.get().getContent()))).isEqualTo(MqStatus.ACCEPTED);
    }
}