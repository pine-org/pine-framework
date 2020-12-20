package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.AbstractFlatEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.ProductTransformer;
import com.pineframework.core.tutorial.eshop.model.ProductModel;

public class ProductEntityServiceImpl extends AbstractFlatEntityService
        <Long, ProductEntity, ProductModel, ProductModel.Builder, ProductTransformer, ProductRepository>
        implements ProductEntityService {

    public ProductEntityServiceImpl(ProductRepository repository, ProductTransformer transformer) {
        super(repository, transformer);
    }
}
