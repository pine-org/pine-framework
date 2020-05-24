package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

/**
 * Strategy pattern.
 * All path generator for tree structure must implements {@code PathGeneratorStrategy}
 *
 * @param <I> id
 * @param <E> entity
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface PathGeneratorStrategy<I extends Serializable, E extends TreePersistence<I, E>> {

    /**
     * @param entity
     * @param path
     */
    void setPath(E entity, String path);
}
