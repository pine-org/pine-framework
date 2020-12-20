package com.pineframework.core.tutorial.eshop.business.transformer;

import com.pineframework.core.business.transformer.AbstractImmutableFlatTransformer;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;
import com.pineframework.core.tutorial.eshop.business.domain.ProductPhotoEntity;
import com.pineframework.core.tutorial.eshop.model.ProductModel;
import com.pineframework.core.tutorial.eshop.model.ProductModel.Builder;

import java.util.stream.Collectors;

import static com.pineframework.core.helper.CollectionUtils.contains;
import static com.pineframework.core.helper.CollectionUtils.isThereAnyElement;

public class ProductTransformer extends AbstractImmutableFlatTransformer<Long, ProductEntity, ProductModel, Builder> {

    @Override
    public Builder getModelBuilder(ProductEntity e) {
        return new Builder(e.getName(), e.getCode());
    }

    @Override
    protected void transformEntityToModel(ProductEntity input, Builder outputBuilder, int deep, String... field) {
        outputBuilder.price(input.getPrice());
        outputBuilder.description(input.getDescription());

        isThereAnyElement(field)
                .ifTrue(() -> {
                    contains(field, "photo"::equals).ifTrue(() -> outputBuilder.photo(input.getPhoto()));
                    contains(field, "photos"::equals).ifTrue(() -> outputBuilder.photos(input.getPhotos()
                            .stream().map(ProductPhotoEntity::getContent).collect(Collectors.toList())));
                });
    }

    @Override
    protected void transformModelToEntity(ProductModel input, ProductEntity output, int deep, String... field) {
        output.setName(input.getName());
        output.setCode(input.getCode());
        output.setPrice(input.getPrice());
        output.setDescription(input.getDescription());
        output.setPhoto(input.getPhoto());
    }
}
