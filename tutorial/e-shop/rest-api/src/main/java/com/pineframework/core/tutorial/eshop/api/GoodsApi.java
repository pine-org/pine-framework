package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.spring.restapi.restcontroller.AbstractRestApi;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "e-shop/api/v1/goods", tags = {"E Shop Goods API"})
@RestController
@RequestMapping("/api/v1/goods")
public class GoodsApi extends AbstractRestApi<Long, GoodsModel, GoodsEntityService> {

    @Autowired
    public GoodsApi(GoodsEntityService service) {
        super(service);
    }

}