package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;

public interface GoodsRepository extends CrudRepository<Long, GoodsEntity>, QueryRepository<Long, GoodsEntity>,
        BatchRepository<Long, GoodsEntity> {
}
