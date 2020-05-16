package com.pineframework.core.contract.service;

import com.pineframework.core.datastructure.model.FlatTransient;
import com.pineframework.core.datastructure.persistence.FlatPersistence;

import java.io.Serializable;

public interface AroundServiceOperation<I extends Serializable, M extends FlatTransient<I>,
        E extends FlatPersistence<I>> {

    default void beforeSave(E e, M m) {
    }

    default void afterSave(E e, M m) {
    }

    default void beforeUpdate(E e, M m) {
    }

    default void afterUpdate(E e, M m, M oldData) {
    }

    default void beforeDelete(M m) {
    }

    default void afterDelete(M m) {
    }
}
