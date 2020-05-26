package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.List;

public interface ImmutableFlatTransformer<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        B extends FlatTransient.Builder<I, M, B>> {

    E createEntity();

    B getBuilder(E e);

    M transform(E e, int deep, String... fields);

    E transform(M m, int deep, String... field);

    E transform(M m, E e, int deep, String... field);

    M transform(E e, String... field);

    E transform(M m, String... field);

    E transform(M m, E e, String... field);

    M[] transform(E[] entities, int deep, String... field);

    M[] transform(E[] entities, String... field);

    E[] transform(M[] models, int deep, String... field);

    E[] transform(M[] models, String... field);

    List<M> transformToModels(List<E> entities, int deep, String... field);

    List<M> transformToModels(List<E> entities, String... field);

    List<E> transformToEntities(List<M> models, int deep, String... field);

    List<E> transformToEntities(List<M> models, String... field);

}
