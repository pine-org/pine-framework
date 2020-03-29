package com.pineframework.core.sample.activemq.api;

import com.pineframework.core.messaging.activemq.service.QueueServiceProxy;
import com.pineframework.core.sample.activemq.model.SampleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("api")
public class ActiveMqApi {

    @Autowired
    private QueueServiceProxy queueServiceProxy;

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ResponseEntity<String> sendToQueue(@RequestBody SampleModel model) {
        queueServiceProxy.publish(model);
        return ResponseEntity.ok(model.getCorrelationId());
    }

    @GetMapping
    public ResponseEntity<String> getLocalMessage(Locale locale) {
        return ResponseEntity.ok(messageSource.getMessage("test.internationalization", null, locale));
    }

}