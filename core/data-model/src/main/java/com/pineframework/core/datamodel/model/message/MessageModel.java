package com.pineframework.core.datamodel.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pineframework.core.datamodel.model.FlatTransient;
import io.vavr.control.Try;

@JsonDeserialize(builder = MessageModel.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageModel extends FlatTransient<String> {

    private final Object content;

    protected MessageModel(MessageModel.Builder builder) {
        super(builder);
        this.content = builder.content;
    }

    public Object getContent() {
        return content;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder extends FlatTransient.Builder<String, MessageModel, MessageModel.Builder> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        private Object content;

        public MessageModel.Builder content(Object content) {
            this.content = content;
            return this;
        }

        @Override
        public MessageModel.Builder from(MessageModel model) {
            super.from(model);
            this.content = model.getContent();
            return this;
        }

        public MessageModel.Builder fromJson(String json) {
            return from(Try.of(() -> objectMapper.readValue(json, MessageModel.class)).get());
        }

        @Override
        public MessageModel build() {
            return new MessageModel(this);
        }
    }
}
