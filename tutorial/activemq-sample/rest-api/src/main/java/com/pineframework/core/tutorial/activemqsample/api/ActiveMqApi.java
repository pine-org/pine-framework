package com.pineframework.core.tutorial.activemqsample.api;

import com.pineframework.core.business.service.QueueService;
import com.pineframework.core.tutorial.activemqsample.model.SampleModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Locale;

@Api(value = "activemq-sample/v1/api", tags = {"Active MQ Sample API"})
@RestController
@RequestMapping("v1/api")
public class ActiveMqApi<I extends Serializable> {

    @Autowired
    @Qualifier("sampleMainQueueService")
    private QueueService<String, SampleModel> service;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PostMapping
    public ResponseEntity<String> sendToQueue(@RequestBody SampleModel model) {
        String id = service.save(model);
        return ResponseEntity.ok(model.getId());
    }

    @GetMapping
    public ResponseEntity<String> getLocalMessage(@RequestParam(name = "name") String name, Locale locale) {
        return ResponseEntity.ok(messageSource.getMessage(name, null, locale));
    }

}