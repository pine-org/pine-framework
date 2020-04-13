package com.pineframework.core.contract.transformer;

import com.pineframework.core.datastructure.model.FlatTransient;
import com.pineframework.core.datastructure.persistence.FlatPersistence;

import java.io.Serializable;

public interface AdditionalTransformer<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>> {

    default void addToModel(E e, M m, int deep, String... fields) {
    }

    default void addToEntity(E e, M m, int deep, String... fields) {
    }
}
