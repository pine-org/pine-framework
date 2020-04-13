package com.pineframework.core.datastructure.persistence;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @param <I> id
 * @param <E> entity
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class TreePersistence<I extends Serializable, E extends TreePersistence>
        extends FlatPersistence<I> {

    protected E parent;

    protected List<E> children = new ArrayList<>();

    protected String path;

    /**
     * @return parent entity
     */
    @Transient
    public E getParent() {
        return parent;
    }

    /**
     * @param parent
     */
    public void setParent(E parent) {
        this.parent = parent;
    }

    /**
     * @return children
     */
    @Transient
    public List<E> getChildren() {
        return children;
    }

    /**
     * @param children
     */
    public void setChildren(List<E> children) {
        this.children = children;
    }

    /**
     * @return path of node
     */
    @Transient
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * use for send custom information to other layers or clients
     *
     * @return object
     */
    @Transient
    public Object getAdditionalInfo() {
        return getId();
    }

    /**
     * check the node is leaf or not
     *
     * @return boolean
     */
    @Transient
    public boolean isLeaf() {
        return Objects.isNull(getChildren()) || getChildren().isEmpty();
    }
}
