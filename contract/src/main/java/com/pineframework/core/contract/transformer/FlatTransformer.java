package com.pineframework.core.contract.transformer;

import com.pineframework.core.helper.GenericUtils;
import com.pineframework.core.datastructure.model.FlatTransient;
import com.pineframework.core.datastructure.persistence.FlatPersistence;
import io.vavr.control.Try;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.pineframework.core.helper.CollectionUtils.EMPTY_LIST;
import static com.pineframework.core.helper.CollectionUtils.createArray;
import static com.pineframework.core.helper.CollectionUtils.isEmpty;
import static com.pineframework.core.helper.CollectionUtils.map;

/**
 * @author Saman Alishiri
 */
public abstract class FlatTransformer<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>>
        implements Transformer<I, M, E>, AdditionalTransformer<I, M, E> {

    public static final int END_DEEP = -1;

    public static final int FIRST_DEEP = 1;

    public static final int EXIT = 0;

    protected Class<? extends FlatTransient> getModelType() {
        return (Class<M>) GenericUtils.extract(this.getClass(), 1);
    }

    protected Class<? extends FlatPersistence> getEntityType() {
        return (Class<E>) GenericUtils.extract(this.getClass(), 2);
    }

    @Override
    public E createEntity() {
        return Try.of(() -> (E) getEntityType().newInstance()).get();
    }

    @Override
    public M createModel() {
        return Try.of(() -> (M) getModelType().newInstance()).get();
    }

    @Override
    public void addToModel(E e, M m, int deep, String... field) {
        AdditionalTransformer.super.addToModel(e, m, deep, field);
    }

    @Override
    public void addToEntity(E e, M m, int deep, String... fields) {
        AdditionalTransformer.super.addToEntity(e, m, deep, fields);
    }

    protected abstract void transformEntityToModel(E input, M output, int deep, String... field);

    protected abstract void transformModelToEntity(M input, E output, int deep, String... field);

    protected void addSystematicFieldsToModel(E e, M m) {
        m.setId(e.getId());
        m.setVersion(e.getVersion());
        m.setInsertDate(e.getInsertDate());
        m.setInsertUserId(e.getInsertUserId());
        m.setInsertUnitId(e.getInsertUnitId());
        m.setModifyDate(e.getModifyDate());
        m.setModifyUserId(e.getModifyUserId());
        m.setModifyUnitId(e.getModifyUnitId());
    }

    private void addSystematicFieldsToEntity(E e, M m) {
        e.setId(m.getId());
        e.setVersion(m.getVersion());
    }

    @Override
    public M transform(E e, int deep, String... fields) {
        M m = createModel();

        if (Objects.isNull(e) || (deep == EXIT))
            return null;

        transformEntityToModel(e, m, deep, fields);
        addSystematicFieldsToModel(e, m);
        addToModel(e, m, deep, fields);
        return m;
    }

    @Override
    public E transform(M m, int deep, String... field) {
        return transform(m, createEntity(), deep, field);
    }

    @Override
    public E transform(M m, E e, int deep, String... field) {
        if (Objects.isNull(e) || Objects.isNull(m)) return null;
        transformModelToEntity(m, e, deep, field);
        addSystematicFieldsToEntity(e, m);
        addToEntity(e, m, deep, field);
        return e;
    }

    @Override
    public M transform(E e, String... field) {
        return transform(e, FIRST_DEEP, field);
    }

    @Override
    public E transform(M m, String... field) {
        return transform(m, FIRST_DEEP, field);
    }

    @Override
    public E transform(M m, E e, String... field) {
        return transform(m, e, FIRST_DEEP, field);
    }

    public M[] transform(E[] entities, int deep, String... field) {
        return isEmpty(entities)
                ? createArray(getModelType(), 0)
                : map(entities, e -> transform(e, deep, field), getModelType());
    }

    public E[] transform(M[] models, int deep, String... field) {
        return isEmpty(models)
                ? createArray(getEntityType(), 0)
                : map(models, e -> transform(e, deep, field), getEntityType());
    }

    @Override
    public List<M> transformEntitiesToModels(List<E> entities, int deep, String... field) {
        return isEmpty(entities) ? EMPTY_LIST : map(entities, e -> transform(e, deep, field));
    }

    @Override
    public List<M> transformEntitiesToModels(List<E> entities, String... field) {
        return transformEntitiesToModels(entities, FIRST_DEEP, field);
    }

    @Override
    public List<E> transformModelsToEntities(List<M> models, int deep, String... field) {
        return isEmpty(models) ? EMPTY_LIST : map(models, m -> transform(m, deep, field));
    }

    @Override
    public List<E> transformModelsToEntities(List<M> models, String... field) {
        return transformModelsToEntities(models, FIRST_DEEP, field);
    }
}
