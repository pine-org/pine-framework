package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface FlatRepository<I extends Serializable, E extends FlatPersistence<I>> {

    Class<E> getType();

    Repository getImpl();

    default void flush() {
        getImpl().flush();
    }

    default void clear() {
        getImpl().clear();
    }
}