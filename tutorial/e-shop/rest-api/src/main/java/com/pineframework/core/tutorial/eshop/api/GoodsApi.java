package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.rest.api.AbstractRestApi;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "e-shop/v1/api/goods", tags = {"E Shop Goods API"})
@RestController
@RequestMapping("v1/api/goods")
public class GoodsApi extends AbstractRestApi<Long, GoodsModel, GoodsEntityService> {

    @Autowired
    public GoodsApi(GoodsEntityService service) {
        super(service);
    }

}