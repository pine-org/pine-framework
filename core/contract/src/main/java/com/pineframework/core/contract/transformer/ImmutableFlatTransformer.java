package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Transformer for transient object to persistable object and vice versa.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface ImmutableFlatTransformer<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>> {

    E createEntity();

    B getModelBuilder(E e);

    M transform(E e, int deep, Map<String, Object> params, String... fields);

    E transform(M m, int deep, Map<String, Object> params, String... field);

    E transform(M m, E e, int deep, Map<String, Object> params, String... field);

    M transform(E e, Map<String, Object> params, String... field);

    E transform(M m, Map<String, Object> params, String... field);

    void transform(M m, E e, Map<String, Object> params, String... field);

    M[] transform(E[] entities, int deep, Map<String, Object> params, String... field);

    M[] transform(E[] entities, Map<String, Object> params, String... field);

    E[] transform(M[] models, int deep, Map<String, Object> params, String... field);

    E[] transform(M[] models, Map<String, Object> params, String... field);

    List<M> transformToModels(List<E> entities, int deep, Map<String, Object> params, String... field);

    List<M> transformToModels(List<E> entities, Map<String, Object> params, String... field);

    List<E> transformToEntities(List<M> models, int deep, Map<String, Object> params, String... field);

    List<E> transformToEntities(List<M> models, Map<String, Object> params, String... field);

}
