package com.pineframework.core.sample.activemq.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pineframework.core.messaging.activemq.config.MessagePublisher.publish;

@RestController
@RequestMapping("api")
public class ActiveMQApi {

    @PostMapping
    public void persistToQueue(@RequestBody String message) {
        publish(message);
        ResponseEntity.ok();
    }

}