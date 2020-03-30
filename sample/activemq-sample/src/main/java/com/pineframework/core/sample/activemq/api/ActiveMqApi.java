package com.pineframework.core.sample.activemq.api;

import com.pineframework.core.messaging.activemq.service.QueueServiceProxy;
import com.pineframework.core.sample.activemq.model.SampleModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Api(value = "example/api/v1", description = "", tags = {"Example API"})
@RestController
@RequestMapping("api/v1")
public class ActiveMqApi {

    @Autowired
    private QueueServiceProxy queueServiceProxy;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PostMapping
    public ResponseEntity<String> sendToQueue(@RequestBody SampleModel model) {
        queueServiceProxy.publish(model);
        return ResponseEntity.ok(model.getCorrelationId());
    }

    @GetMapping
    public ResponseEntity<String> getLocalMessage(@RequestParam(name = "name") String name, Locale locale) {
        return ResponseEntity.ok(messageSource.getMessage(name, null, locale));
    }

}