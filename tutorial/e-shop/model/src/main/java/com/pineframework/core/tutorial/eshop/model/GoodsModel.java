package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.model.TransientBlobSupport;
import com.pineframework.core.datamodel.validation.CreateValidationGroup;
import com.pineframework.core.datamodel.validation.UpdateValidationGroup;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@SuppressWarnings({"rawtypes", "unchecked"})
@JsonDeserialize(builder = GoodsModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GoodsModel extends FlatTransient<Long> implements TransientBlobSupport {

    @NotBlank(message = "error.validation.notBlank",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private final String name;

    @NotBlank(message = "error.validation.notBlank",
            groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private final String code;

    private final BigDecimal price;

    private final String description;

    private final byte[] photo;

    public GoodsModel(GoodsModel.Builder builder) {
        super(builder);
        this.name = builder.name;
        this.code = builder.code;
        this.price = builder.price;
        this.description = builder.description;
        this.photo = builder.photo;
    }

    public <T extends FlatTransient<Long>> FlatTransient.Builder replace(T m) {
        GoodsModel model = (GoodsModel) m;
        return new GoodsModel.Builder(model.getName(), model.getCode()).from(model);
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

    @JsonIgnore
    public <T> T getDynamicData(String name) {
        return (T) getDynamicData().get(name);
    }

    @SuppressWarnings("UnusedReturnValue")
    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder extends FlatTransient.Builder<Long, GoodsModel, GoodsModel.Builder> {

        private final String name;

        private final String code;

        public byte[] photo;

        private BigDecimal price;

        private String description;

        @JsonCreator
        public Builder(@JsonProperty("name") String name, @JsonProperty("code") String code) {
            super();
            this.name = name;
            this.code = code;
        }

        @JsonIgnore
        @Override
        public GoodsModel.Builder from(GoodsModel model) {
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

        public GoodsModel build() {
            return new GoodsModel(this);
        }
    }
}
