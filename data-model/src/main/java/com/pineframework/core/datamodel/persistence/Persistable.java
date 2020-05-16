package com.pineframework.core.datamodel.persistence;

import java.io.Serializable;

/**
 * all entities must implements {@code Persistable}
 *
 * @param <I> id
 * @author Saman Alishiri
 */
public interface Persistable<I extends Serializable> {

    /**
     * @return id
     */
    I getId();

    /**
     * @return version
     */
    Integer getVersion();
}
