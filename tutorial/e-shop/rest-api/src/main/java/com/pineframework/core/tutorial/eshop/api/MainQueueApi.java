package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;
import com.pineframework.core.spring.restapi.restcontroller.AbstractQueueRestApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "e-shop/api/v1/queue", description = "E Shop Queue API")
@RestController
@RequestMapping("/api/v1/queue")
@Profile(value = "activemq")
public class MainQueueApi extends AbstractQueueRestApi<String, MessageModel, QueueService<String, MessageModel>> {

    @Autowired
    public MainQueueApi(@Qualifier("mainQueueService") QueueService service) {
        super(service);
    }
}