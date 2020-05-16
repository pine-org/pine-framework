package com.pineframework.core.datastructure.model;

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

    private M parent;

    private List<M> children = new ArrayList<>();

    private String path;

    private Boolean isLeaf;

    public Object getDedicatedData() {
        return getId();
    }

    public M getParent() {
        return parent;
    }

    public void setParent(M parent) {
        this.parent = parent;
    }

    public List<M> getChildren() {
        return children;
    }

    public void setChildren(List<M> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

}
