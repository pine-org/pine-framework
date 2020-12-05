package com.pineframework.core.datamodel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * All models such as value objects, data transfer object (DTO), etc should be extended from {@code AbstractTransient}.
 * {@code AbstractTransient} implements equals and hash code and also {@code toString} method.
 *
 * @param <I> the type of identity
 */
public abstract class TreeTransient<I extends Serializable, M extends TreeTransient>
        extends FlatTransient<I> {

    protected final M parent;

    protected final List<M> children;

    protected final String path;

    protected final Boolean isLeaf;

    protected TreeTransient(Builder builder) {
        super(builder);
        this.parent = (M) builder.parent;
        this.children = builder.children;
        this.path = builder.path;
        this.isLeaf = builder.isLeaf;
    }

    public Object getDedicatedData() {
        return getId();
    }

    public M getParent() {
        return parent;
    }

    public List<M> getChildren() {
        return children;
    }

    public String getPath() {
        return path;
    }

    public Boolean isLeaf() {
        return isLeaf;
    }

    public abstract static class Builder<I extends Serializable, M extends TreeTransient<I, M>,
            B extends FlatTransient.Builder<I, M, B>>
            extends FlatTransient.Builder<I, M, B> {

        private M parent;

        private List<M> children = new ArrayList<>();

        private String path;

        private Boolean isLeaf;

        public Builder() {
            super();
        }

        public B parent(M parent) {
            this.parent = parent;
            return (B) this;
        }

        public B children(List<M> children) {
            this.children = children;
            return (B) this;
        }

        public B path(String path) {
            this.path = path;
            return (B) this;
        }

        public B isLeaf(Boolean isLeaf) {
            this.isLeaf = isLeaf;
            return (B) this;
        }

        @Override
        public B from(M model) {
            super.from(model);
            this.parent = model.getParent();
            this.children = model.getChildren();
            this.path = model.getPath();
            this.isLeaf = model.isLeaf;
            return (B) this;
        }

        @Override
        public abstract M build();
    }

}
