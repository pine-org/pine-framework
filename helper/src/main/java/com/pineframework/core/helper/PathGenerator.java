package com.pineframework.core.helper;

import com.pineframework.core.datastructure.persistence.TreePersistence;

import java.io.Serializable;

/**
 * Strategy pattern.
 * All path generator for tree structure must implements {@code PathGeneratorStrategy}
 *
 * @param <I> id
 * @param <E> entity
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface PathGenerator<I extends Serializable, E extends TreePersistence<I, E>> {

    /**
     * @param entity
     * @param parentPath
     */
    void setPath(E entity, String parentPath);
}
