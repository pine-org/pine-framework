package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface AroundServiceOperation<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>> {

    default void beforeSave(E e, M m) {
    }

    default void afterSave(E e, M m) {
    }

    default void beforeUpdate(E e, M m) {
    }

    default void afterUpdate(E e, M theLast) {
    }

    default void beforeDelete(M m) {
    }

    default void afterDelete(M m) {
    }
}
