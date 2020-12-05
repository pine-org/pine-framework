package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * Provide functions to execute after and before the CRUD functions of business logic service.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

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
