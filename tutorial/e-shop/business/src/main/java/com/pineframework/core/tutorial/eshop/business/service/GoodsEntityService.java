package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;

public interface GoodsEntityService extends
        CrudEntityService<Long, GoodsEntity, GoodsModel, GoodsModel.Builder, GoodsTransformer, GoodsRepository>,
        QueryEntityService<Long, GoodsEntity, GoodsModel, GoodsModel.Builder, GoodsTransformer, GoodsRepository>,
        BatchEntityService<Long, GoodsEntity, GoodsModel, GoodsModel.Builder, GoodsTransformer, GoodsRepository> {
}
