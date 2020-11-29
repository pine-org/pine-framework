package com.pineframework.core.tutorial.eshop.business.transformer;

import com.pineframework.core.business.transformer.AbstractImmutableFlatTransformer;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import com.pineframework.core.tutorial.eshop.model.GoodsModel.Builder;

import java.util.Map;

public class GoodsTransformer extends AbstractImmutableFlatTransformer<Long, GoodsEntity, GoodsModel, Builder> {

    @Override
    public Builder getModelBuilder(GoodsEntity e) {
        return new Builder(e.getName(), e.getCode());
    }

    @Override
    protected void transformEntityToModel(GoodsEntity input, Builder outputBuilder, int deep,
                                          Map<String, Object> params, String... field) {
        outputBuilder.price(input.getPrice());
        outputBuilder.description(input.getDescription());
    }

    @Override
    protected void transformModelToEntity(GoodsModel input, GoodsEntity output, int deep,
                                          Map<String, Object> params, String... field) {
        output.setName(input.getName());
        output.setCode(input.getCode());
        output.setPrice(input.getPrice());
        output.setDescription(input.getDescription());
        output.setPhoto(input.getPhoto());
    }
}
