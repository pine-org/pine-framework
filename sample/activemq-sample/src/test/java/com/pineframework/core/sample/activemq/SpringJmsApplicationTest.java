package com.pineframework.core.sample.activemq;

import com.pineframework.core.messaging.activemq.config.QueueHelper;
import com.pineframework.core.sample.activemq.model.SampleModel;
import com.pineframework.core.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("sample activemq-queue test ")
@SpringBootTest
public class SpringJmsApplicationTest extends AbstractTest {

    @Autowired
    private QueueHelper queueHelper;

    @DisplayName("send message get OK status")
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void publish_SendMessages_GetAcceptedStatus(String text) {
        SampleModel model = new SampleModel();
        model.setMessage(text);

        queueHelper.publish(model);
        queueHelper.readStatus(model);
        infoLog(model);

        assertThat(model.getStatus()).isEqualTo("Accepted");
    }
}