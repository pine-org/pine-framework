package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * In order to add additional states to the transformer.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient builder
 */
public interface ImmutableAdditionalTransformer<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> {

    default void addToModel(E e, B builder, int deep, String... fields) {
    }

    default void addToEntity(E e, M m, int deep, String... fields) {
    }
}
