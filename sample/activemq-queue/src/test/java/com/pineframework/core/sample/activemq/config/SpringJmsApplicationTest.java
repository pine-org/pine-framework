package com.pineframework.core.sample.activemq.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static com.pineframework.core.sample.activemq.config.MessagePublisher.getStatus;
import static com.pineframework.core.sample.activemq.config.MessagePublisher.publish;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("sample activemq-queue test ")
@SpringBootTest
public class SpringJmsApplicationTest {

    @DisplayName("send message get OK status")
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Great", "Bye"})
    public void publish_SendStringMessages_GetAcceptedStatus(String text) {
        String correlationID = publish(text);
        String status = getStatus(correlationID);
        assertThat(status).isEqualTo("OK");
    }
}