package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.AbstractFlatEntityService;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsEntityService extends AbstractFlatEntityService<Long, GoodsModel,
        GoodsEntity, GoodsRepository,
        GoodsModel.Builder,
        GoodsTransformer> {

    @Autowired
    public GoodsEntityService(GoodsRepository repository, GoodsTransformer transformer) {
        super(repository, transformer);
    }
}
