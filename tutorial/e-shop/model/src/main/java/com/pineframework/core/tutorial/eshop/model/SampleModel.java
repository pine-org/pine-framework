package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.datamodel.model.message.AbstractMessageModel;

@JsonDeserialize(builder = SampleModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class SampleModel extends AbstractMessageModel<String> {

    private SampleModel(SampleModel.Builder builder) {
        super(builder);
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder extends AbstractMessageModel.Builder<String, SampleModel, SampleModel.Builder> {

        public Builder() {
            super();
        }

        @Override
        public SampleModel.Builder from(SampleModel model) {
            super.from(model);
            return this;
        }

        @Override
        public SampleModel build() {
            return new SampleModel(this);
        }
    }
}
