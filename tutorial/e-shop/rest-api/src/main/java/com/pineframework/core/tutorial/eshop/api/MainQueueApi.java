package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.message.MessageModel;
import com.pineframework.core.rest.api.AbstractQueueRestApi;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "e-shop/api/v1/queue", tags = {"E Shop Queue API"})
@RestController
@RequestMapping("/api/v1/queue")
public class MainQueueApi extends AbstractQueueRestApi<String, MessageModel, QueueService<String, MessageModel>> {

    @Autowired
    public MainQueueApi(@Qualifier("mainQueueService") QueueService service) {
        super(service);
    }
}