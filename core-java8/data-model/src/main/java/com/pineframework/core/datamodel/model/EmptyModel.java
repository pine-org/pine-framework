package com.pineframework.core.datamodel.model;

public class EmptyModel extends FlatTransient<String> {

    private static final long serialVersionUID = 7595836524580695687L;

    private EmptyModel(EmptyModel.Builder builder) {
        super(builder);
    }

    public static final class Builder extends FlatTransient.Builder<String, EmptyModel, EmptyModel.Builder> {
        @Override
        public EmptyModel build() {
            return new EmptyModel(this);
        }
    }
}
