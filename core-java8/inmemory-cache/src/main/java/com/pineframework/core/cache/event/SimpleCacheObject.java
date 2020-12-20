package com.pineframework.core.cache.event;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public final class SimpleCacheObject implements CacheObject {

    private final String key;

    private final Object id;

    private final Object data;

    private final RowOperation operation;

    private final String className;

    private SimpleCacheObject(Builder builder) {
        key = builder.key;
        id = builder.id;
        data = builder.data;
        className = builder.className;
        operation = builder.operation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Object getValue() {
        return data;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public RowOperation getOperation() {
        return operation;
    }

    public static final class Builder {

        private String key;

        private Object id;

        private Object data;

        private String className;

        private RowOperation operation;

        private Builder() {
        }

        public Builder key(String val) {
            key = val;
            return this;
        }

        public Builder className(String val) {
            className = val;
            return this;
        }

        public Builder id(Object val) {
            id = val;
            return this;
        }

        public Builder data(Object val) {
            data = val;
            return this;
        }

        public Builder operation(RowOperation val) {
            operation = val;
            return this;
        }

        public SimpleCacheObject build() {
            return new SimpleCacheObject(this);
        }
    }
}
