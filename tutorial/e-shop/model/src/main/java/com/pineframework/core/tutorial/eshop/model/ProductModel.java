package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.cache.annotation.CacheData;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.validation.CreateValidationGroup;
import com.pineframework.core.datamodel.validation.UpdateValidationGroup;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = ProductModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@CacheData(schema = "PINE",
        tableName = "PRODUCT",
        parent = FlatTransient.class,
        firstLoad = true)
public final class ProductModel extends FlatTransient<Long> {

    @NotBlank(message = "error.validation.notBlank",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private final String name;

    @NotBlank(message = "error.validation.notBlank",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private final String code;

    private final BigDecimal price;

    private final String description;

    private final byte[] photo;

    private final List<byte[]> photos;

    public ProductModel(ProductModel.Builder builder) {
        super(builder);
        this.name = builder.name;
        this.code = builder.code;
        this.price = builder.price;
        this.description = builder.description;
        this.photo = builder.photo;
        this.photos = builder.photos;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public List<byte[]> getPhotos() {
        return photos;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder extends FlatTransient.Builder<Long, ProductModel, ProductModel.Builder> {

        private final String name;

        private final String code;

        private BigDecimal price;

        private String description;

        public byte[] photo;

        private List<byte[]> photos = new ArrayList<>();

        @JsonCreator
        public Builder(@JsonProperty("name") String name, @JsonProperty("code") String code) {
            super();
            this.name = name;
            this.code = code;
        }

        @JsonIgnore
        @Override
        public ProductModel.Builder from(ProductModel model) {
            super.from(model);
            this.price = model.getPrice();
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder photo(byte[] photo) {
            this.photo = photo;
            return this;
        }

        public Builder photos(List<byte[]> photos) {
            this.photos = photos;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(this);
        }
    }
}
