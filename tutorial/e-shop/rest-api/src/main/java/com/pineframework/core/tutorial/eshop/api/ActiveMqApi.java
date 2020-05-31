package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
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
import java.util.Optional;

@Api(value = "e-shop/v1/api", tags = {"E Shop API"})
@RestController
@RequestMapping("v1/api")
public class ActiveMqApi<I extends Serializable> {

    @Autowired
    @Qualifier("mainQueueService")
    private QueueService<String, MessageModel, MessageModel.Builder> service;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PostMapping
    public ResponseEntity<String> sendToQueue(@RequestBody MessageModel m) {
        Optional<MessageModel> model = service.save(m);
        return ResponseEntity.ok(model.get().getId());
    }

    @GetMapping
    public ResponseEntity<String> getLocalMessage(@RequestParam(name = "name") String name, Locale locale) {
        return ResponseEntity.ok(messageSource.getMessage(name, null, locale));
    }

}