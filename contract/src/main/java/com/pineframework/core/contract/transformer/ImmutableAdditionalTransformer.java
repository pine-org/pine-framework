package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface ImmutableAdditionalTransformer<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        B extends FlatTransient.Builder<I, M, B>> {

    default void addToModel(E e, B builder, int deep, String... fields) {
    }

    default void addToEntity(E e, M m, int deep, String... fields) {
    }
}
