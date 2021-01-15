package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.spring.restapi.restcontroller.AbstractRestApi;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityService;
import com.pineframework.core.tutorial.eshop.model.ProductModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "eshop/api/v1/product", description = "E Shop Product API")
@RestController
@RequestMapping("/api/v1/product")
public class ProductApi extends AbstractRestApi<Long, ProductModel, ProductEntityService> {

    @Autowired
    public ProductApi(ProductEntityService service) {
        super(service);
    }

}