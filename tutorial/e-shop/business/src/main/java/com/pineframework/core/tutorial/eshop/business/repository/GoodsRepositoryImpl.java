package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.business.repository.AbstractFlatRepository;
import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;

public class GoodsRepositoryImpl extends AbstractFlatRepository<Long, GoodsEntity>
        implements GoodsRepository {

    public GoodsRepositoryImpl(JpaRepository repository) {
        super(repository);
    }
}
