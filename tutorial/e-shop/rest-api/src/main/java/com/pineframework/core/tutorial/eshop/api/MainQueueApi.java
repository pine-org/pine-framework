package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.rest.api.AbstractRestApi;
import com.pineframework.core.tutorial.eshop.business.service.MainQueueService;
import com.pineframework.core.tutorial.eshop.model.MessageModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "e-shop/v1/api", tags = {"E Shop API"})
@RestController
@RequestMapping("v1/api/queue")
public class MainQueueApi extends AbstractRestApi<String, MessageModel, MainQueueService> {

    @Autowired
    public MainQueueApi(MainQueueService service) {
        super(service);
    }

}