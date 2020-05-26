package com.pineframework.core.tutorial.eshop.business.repository;

import com.pineframework.core.business.repository.AbstractFlatRepository;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsRepository extends AbstractFlatRepository<Long, GoodsEntity> {
}
