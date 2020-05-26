package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.datamodel.model.FlatTransient;

import java.math.BigDecimal;

@JsonDeserialize(builder = GoodsModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GoodsModel extends FlatTransient<Long> {

    private final String name;

    private final String code;

    private final BigDecimal price;

    public GoodsModel(GoodsModel.Builder builder) {
        super(builder);
        this.name = builder.name;
        this.code = builder.code;
        this.price = builder.price;
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

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder extends FlatTransient.Builder<Long, GoodsModel, GoodsModel.Builder> {

        private String name;

        private String code;

        private BigDecimal price;

        @JsonCreator
        public Builder(@JsonProperty("name") String name, @JsonProperty("code") String code) {
            super();
            this.name = name;
            this.code = code;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        @JsonIgnore
        @Override
        public GoodsModel.Builder from(GoodsModel model) {
            super.from(model);
            this.price = model.getPrice();
            return this;
        }

        public GoodsModel build() {
            return new GoodsModel(this);
        }
    }
}