package com.pineframework.core.business.transformer;

import com.pineframework.core.contract.transformer.ImmutableAdditionalTransformer;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.GenericUtils;
import io.vavr.control.Try;

import java.io.Serializable;
import java.util.List;

import static com.pineframework.core.helper.CollectionUtils.EMPTY_LIST;
import static com.pineframework.core.helper.CollectionUtils.createArray;
import static com.pineframework.core.helper.CollectionUtils.isEmpty;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static java.util.Objects.isNull;

/**
 * @author Saman Alishiri
 */
public abstract class AbstractImmutableFlatTransformer<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        B extends FlatTransient.Builder<I, M, B>>
        implements ImmutableFlatTransformer<I, M, E, B>, ImmutableAdditionalTransformer<I, M, E, B> {

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

    public void addToModel(E e, B builder, int deep, String... field) {
        ImmutableAdditionalTransformer.super.addToModel(e, builder, deep, field);
    }

    @Override
    public void addToEntity(E e, M m, int deep, String... fields) {
        ImmutableAdditionalTransformer.super.addToEntity(e, m, deep, fields);
    }

    protected abstract void transformEntityToModel(E input, B outputBuilder, int deep, String... field);

    protected abstract void transformModelToEntity(M input, E output, int deep, String... field);

    protected void addSystematicFieldsToModel(E e, B builder) {
        builder.id(e.getId())
                .version(e.getVersion())
                .insertDate(e.getInsertDate())
                .insertUserId(e.getInsertUserId())
                .insertUnitId(e.getInsertUnitId())
                .modifyDate(e.getModifyDate())
                .modifyUserId(e.getModifyUserId())
                .modifyUnitId(e.getModifyUnitId());
    }

    private void addSystematicFieldsToEntity(E e, M m) {
        e.setId(m.getId());
        e.setVersion(m.getVersion());
    }

    @Override
    public M transform(E e, int deep, String... fields) {
        B builder = getModelBuilder(e);

        if (isNull(e) || (deep == EXIT))
            return null;

        transformEntityToModel(e, builder, deep, fields);
        addSystematicFieldsToModel(e, builder);
        addToModel(e, builder, deep, fields);
        return builder.build();
    }

    @Override
    public E transform(M m, int deep, String... field) {
        return transform(m, createEntity(), deep, field);
    }

    @Override
    public E transform(M m, E e, int deep, String... field) {
        if (isNull(e) || isNull(m))
            return null;
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

    @Override
    public M[] transform(E[] entities, int deep, String... field) {
        return isEmpty(entities)
                ? createArray(getModelType(), 0)
                : mapTo(entities, e -> transform(e, deep, field), getModelType());
    }

    @Override
    public M[] transform(E[] entities, String... field) {
        return isEmpty(entities)
                ? createArray(getModelType(), 0)
                : mapTo(entities, e -> transform(e, FIRST_DEEP, field), getModelType());
    }

    @Override
    public E[] transform(M[] models, int deep, String... field) {
        return isEmpty(models)
                ? createArray(getEntityType(), 0)
                : mapTo(models, e -> transform(e, deep, field), getEntityType());
    }

    @Override
    public E[] transform(M[] models, String... field) {
        return isEmpty(models)
                ? createArray(getEntityType(), 0)
                : mapTo(models, e -> transform(e, FIRST_DEEP, field), getEntityType());
    }

    @Override
    public List<M> transformToModels(List<E> entities, int deep, String... field) {
        return isEmpty(entities) ? EMPTY_LIST : mapTo(entities, e -> transform(e, deep, field));
    }

    @Override
    public List<M> transformToModels(List<E> entities, String... field) {
        return transformToModels(entities, FIRST_DEEP, field);
    }

    @Override
    public List<E> transformToEntities(List<M> models, int deep, String... field) {
        return isEmpty(models) ? EMPTY_LIST : mapTo(models, m -> transform(m, deep, field));
    }

    @Override
    public List<E> transformToEntities(List<M> models, String... field) {
        return transformToEntities(models, FIRST_DEEP, field);
    }

}
