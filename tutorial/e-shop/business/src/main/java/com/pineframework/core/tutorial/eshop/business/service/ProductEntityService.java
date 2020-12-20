package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.ProductTransformer;
import com.pineframework.core.tutorial.eshop.model.ProductModel;

public interface ProductEntityService extends
        CrudEntityService
                <Long, ProductEntity, ProductModel, ProductModel.Builder, ProductTransformer, ProductRepository>,
        QueryEntityService
                <Long, ProductEntity, ProductModel, ProductModel.Builder, ProductTransformer, ProductRepository>,
        BatchEntityService
                <Long, ProductEntity, ProductModel, ProductModel.Builder, ProductTransformer, ProductRepository> {
}
