package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.business.repository.AbstractFlatRepository;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;

public class ProductRepositoryImpl extends AbstractFlatRepository<Long, ProductEntity> implements ProductRepository {

    public ProductRepositoryImpl(Repository repository) {
        super(repository);
    }
}
