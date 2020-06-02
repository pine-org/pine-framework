package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.rest.api.AbstractQueueRestApi;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "e-shop/v1/api/queue", tags = {"E Shop Queue API"})
@RestController
@RequestMapping("v1/api/queue")
public class MainQueueApi extends AbstractQueueRestApi<String, MessageModel, MessageModel.Builder,
        QueueService<String, MessageModel, MessageModel.Builder>> {

    @Autowired
    public MainQueueApi(@Qualifier("mainQueueService") QueueService service) {
        super(service);
    }
}