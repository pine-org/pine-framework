package com.pineframework.core.sample.activemq.api;

import com.pineframework.core.messaging.activemq.config.QueueHelper;
import com.pineframework.core.sample.activemq.model.SampleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ActiveMQApi {

    private QueueHelper queueHelper;

    @Autowired
    public void setQueueHelper(QueueHelper queueHelper) {
        this.queueHelper = queueHelper;
    }

    @PostMapping
    public ResponseEntity<String> sendToQueue(@RequestBody SampleModel model) {
        queueHelper.publish(model);
        return ResponseEntity.ok(model.getCorrelationID());
    }

}