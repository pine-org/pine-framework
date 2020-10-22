package com.pineframework.core.tutorial.eshop.business.transformer;

import com.pineframework.core.business.transformer.AbstractImmutableFlatTransformer;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import com.pineframework.core.tutorial.eshop.model.GoodsModel.Builder;

public class GoodsTransformer extends AbstractImmutableFlatTransformer<Long, GoodsModel, GoodsEntity, Builder> {

    @Override
    public Builder getModelBuilder(GoodsEntity e) {
        return new Builder(e.getName(), e.getCode());
    }

    @Override
    protected void transformEntityToModel(GoodsEntity input, Builder outputBuilder, int deep, String... field) {
        outputBuilder.price(input.getPrice());
        outputBuilder.description(input.getDescription());
        outputBuilder.photo(input.getPhoto());
    }

    @Override
    protected void transformModelToEntity(GoodsModel input, GoodsEntity output, int deep, String... field) {
        output.setName(input.getName());
        output.setCode(input.getCode());
        output.setPrice(input.getPrice());
        output.setDescription(input.getDescription());
    }
}
