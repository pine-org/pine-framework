package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.Transient;
import com.pineframework.core.datamodel.persistence.Persistable;

import java.io.Serializable;
import java.util.List;

public interface Transformer<I extends Serializable, M extends Transient, E extends Persistable<I>> {

    E createEntity();

    M createModel();

    M transform(E e, int deep, String... fields);

    E transform(M m, int deep, String... field);

    E transform(M m, E e, int deep, String... field);

    M transform(E e, String... field);

    E transform(M m, String... field);

    E transform(M m, E e, String... field);

    List<M> transformEntitiesToModels(List<E> entities, int deep, String... field);

    List<M> transformEntitiesToModels(List<E> entities, String... field);

    List<E> transformModelsToEntities(List<M> models, int deep, String... field);

    List<E> transformModelsToEntities(List<M> models, String... field);
}
