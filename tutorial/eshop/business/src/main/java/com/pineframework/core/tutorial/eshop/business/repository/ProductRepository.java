package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.contract.repository.flat.BatchRepository;
import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;

public interface ProductRepository extends CrudRepository<Long, ProductEntity>, QueryRepository<Long, ProductEntity>,
        BatchRepository<Long, ProductEntity> {
}
