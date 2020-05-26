package com.pineframework.core.datamodel.model.message;

import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;

public abstract class AbstractMessageModel<I extends Serializable> extends FlatTransient<I> {

    private final Object content;

    protected AbstractMessageModel(AbstractMessageModel.Builder builder) {
        super(builder);
        this.content = builder.content;
    }

    public Object getContent() {
        return content;
    }

    public abstract static class Builder<I extends Serializable, M extends AbstractMessageModel<I>,
            B extends FlatTransient.Builder<I, M, B>>
            extends FlatTransient.Builder<I, M, B> {

        private Object content;

        public B content(Object content) {
            this.content = content;
            return (B) this;
        }

        @Override
        public B from(M model) {
            super.from(model);
            this.content = model.getContent();
            return (B) this;
        }

        @Override
        public abstract M build();
    }
}
