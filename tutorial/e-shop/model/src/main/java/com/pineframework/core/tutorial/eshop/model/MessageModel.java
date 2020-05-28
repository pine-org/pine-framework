package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.datamodel.model.message.AbstractMessageModel;

@JsonDeserialize(builder = MessageModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class MessageModel extends AbstractMessageModel<String> {

    private MessageModel(MessageModel.Builder builder) {
        super(builder);
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder extends AbstractMessageModel.Builder<String, MessageModel, MessageModel.Builder> {

        public Builder() {
            super();
        }

        @Override
        public MessageModel.Builder from(MessageModel model) {
            super.from(model);
            return this;
        }

        @Override
        public MessageModel build() {
            return new MessageModel(this);
        }
    }
}
