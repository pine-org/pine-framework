package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;

public interface GoodsEntityService extends
        CrudEntityService<Long, GoodsModel, GoodsEntity, GoodsRepository, GoodsModel.Builder, GoodsTransformer>,
        QueryEntityService<Long, GoodsModel, GoodsEntity, GoodsRepository, GoodsModel.Builder, GoodsTransformer>,
        BatchEntityService<Long, GoodsModel, GoodsEntity, GoodsRepository, GoodsModel.Builder, GoodsTransformer> {
}
