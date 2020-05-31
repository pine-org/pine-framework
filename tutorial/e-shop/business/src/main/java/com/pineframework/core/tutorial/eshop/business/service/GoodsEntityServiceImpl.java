package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.AbstractFlatEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;

public class GoodsEntityServiceImpl extends
        AbstractFlatEntityService<Long, GoodsModel, GoodsEntity, GoodsRepository, GoodsModel.Builder, GoodsTransformer>
        implements GoodsEntityService {

    public GoodsEntityServiceImpl(GoodsRepository repository, GoodsTransformer transformer) {
        super(repository, transformer);
    }
}
