package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.business.repository.AbstractFlatRepository;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;

public class GoodsRepositoryImpl extends AbstractFlatRepository<Long, GoodsEntity>
        implements GoodsRepository {

    public GoodsRepositoryImpl(Repository repository) {
        super(repository);
    }
}
