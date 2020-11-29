package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Map;

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

    default void transformAdditionalToModel(E e, B builder, int deep, Map<String, Object> params, String... fields) {
    }

    default void transformAdditionalToEntity(E e, M m, int deep, Map<String, Object> params, String... fields) {
    }

    default Boolean condition(E e, M b) {
        return Boolean.TRUE;
    }
}
