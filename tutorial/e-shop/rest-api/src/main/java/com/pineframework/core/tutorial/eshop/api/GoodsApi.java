package com.pineframework.core.tutorial.eshop.api;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Optional;

@Api(value = "e-shop/v1/api", tags = {"E Shop API"})
@RestController
@RequestMapping("v1/api/goods")
public class GoodsApi<I extends Serializable, M extends FlatTransient<I>> {

    @Autowired
    private GoodsEntityService service;

    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody GoodsModel m) {
        Optional<Long> id = service.save(m);
        return ResponseEntity.ok(id.get());
    }

}