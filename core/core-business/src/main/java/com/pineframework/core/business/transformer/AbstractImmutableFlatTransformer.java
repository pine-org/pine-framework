package com.pineframework.core.business.transformer;

import com.pineframework.core.contract.transformer.ImmutableAdditionalTransformer;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.enums.Commands;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.BlobSupport;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.GenericUtils;
import io.vavr.control.Try;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.pineframework.core.datamodel.enums.RequestParameter.COMMANDS;
import static com.pineframework.core.helper.CollectionUtils.EMPTY_LIST;
import static com.pineframework.core.helper.CollectionUtils.createArray;
import static com.pineframework.core.helper.CollectionUtils.forEach;
import static com.pineframework.core.helper.CollectionUtils.isEmpty;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static java.util.Objects.isNull;


/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractImmutableFlatTransformer<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>>
        implements ImmutableFlatTransformer<I, E, M, B>, ImmutableAdditionalTransformer<I, E, M, B> {

    public static final int END_DEEP = -1;

    public static final int FIRST_DEEP = 1;

    public static final int EXIT = 0;

    private final Class<M> modelClassType;

    private final Class<E> entityClassType;

    private final Map<Commands, BiConsumer<?, ?>> commandRepository = new HashMap<>();

    protected AbstractImmutableFlatTransformer() {
        modelClassType = (Class<M>) GenericUtils.extract(this.getClass(), 2);
        entityClassType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
        initCommands();
    }

    private void initCommands() {
        BiConsumer<BlobSupport, B> blob = (blobSupport, b) -> {
            b.dynamicData("defaultBlob", blobSupport.getDefaultBlob());
            b.dynamicData("blobs", blobSupport.getBlobs());
        };
        commandRepository.put(Commands.BLOB, blob);
    }

    private <A, B> BiConsumer<A, B> getCommand(Commands cmd) {
        return (BiConsumer<A, B>) commandRepository.get(cmd);
    }

    protected Class<? extends FlatTransient> getModelType() {
        return modelClassType;
    }

    protected Class<? extends FlatPersistence> getEntityType() {
        return entityClassType;
    }

    @Override
    public E createEntity() {
        return Try.of(() -> (E) getEntityType().newInstance()).get();
    }

    public void transformAdditionalToModel(E e, B builder, int deep, Map<String, Object> params, String... field) {
        ImmutableAdditionalTransformer.super.transformAdditionalToModel(e, builder, deep, params, field);
    }

    @Override
    public void transformAdditionalToEntity(E e, M m, int deep, Map<String, Object> params, String... fields) {
        ImmutableAdditionalTransformer.super.transformAdditionalToEntity(e, m, deep, params, fields);
    }

    protected abstract void transformEntityToModel(E input, B outputBuilder, int deep,
                                                   Map<String, Object> params, String... field);

    protected abstract void transformModelToEntity(M input, E output, int deep,
                                                   Map<String, Object> params, String... field);

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
    public M transform(E e, int deep, Map<String, Object> params, String... fields) {
        B builder = getModelBuilder(e);

        if (isNull(e) || (deep == EXIT))
            return null;

        transformEntityToModel(e, builder, deep, params, fields);
        addSystematicFieldsToModel(e, builder);
        transformAdditionalToModel(e, builder, deep, params, fields);

        params.computeIfPresent(COMMANDS.name(), (k, v) -> {
            forEach(String.valueOf(v).split(";"), Commands::valueOf, cmd -> getCommand(cmd).accept(e, builder));
            return null;
        });

        return builder.build();
    }

    @Override
    public E transform(M m, int deep, Map<String, Object> params, String... field) {
        return transform(m, createEntity(), deep, params, field);
    }

    @Override
    public E transform(M m, E e, int deep, Map<String, Object> params, String... field) {
        if (isNull(e) || isNull(m))
            return null;
        transformModelToEntity(m, e, deep, params, field);
        addSystematicFieldsToEntity(e, m);
        transformAdditionalToEntity(e, m, deep, params, field);
        return e;
    }

    @Override
    public M transform(E e, Map<String, Object> params, String... field) {
        return transform(e, FIRST_DEEP, params, field);
    }

    @Override
    public E transform(M m, Map<String, Object> params, String... field) {
        return transform(m, FIRST_DEEP, params, field);
    }

    @Override
    public void transform(M m, E e, Map<String, Object> params, String... field) {
        transform(m, e, FIRST_DEEP, params, field);
    }

    @Override
    public M[] transform(E[] entities, int deep, Map<String, Object> params, String... field) {
        return isEmpty(entities)
                ? createArray(getModelType(), 0)
                : mapTo(entities, e -> transform(e, deep, params, field), getModelType());
    }

    @Override
    public M[] transform(E[] entities, Map<String, Object> params, String... field) {
        return isEmpty(entities)
                ? createArray(getModelType(), 0)
                : mapTo(entities, e -> transform(e, FIRST_DEEP, params, field), getModelType());
    }

    @Override
    public E[] transform(M[] models, int deep, Map<String, Object> params, String... field) {
        return isEmpty(models)
                ? createArray(getEntityType(), 0)
                : mapTo(models, e -> transform(e, deep, params, field), getEntityType());
    }

    @Override
    public E[] transform(M[] models, Map<String, Object> params, String... field) {
        return isEmpty(models)
                ? createArray(getEntityType(), 0)
                : mapTo(models, e -> transform(e, FIRST_DEEP, params, field), getEntityType());
    }

    @Override
    public List<M> transformToModels(List<E> entities, int deep, Map<String, Object> params, String... field) {
        return isEmpty(entities) ? EMPTY_LIST : mapTo(entities, e -> transform(e, deep, params, field));
    }

    @Override
    public List<M> transformToModels(List<E> entities, Map<String, Object> params, String... field) {
        return transformToModels(entities, FIRST_DEEP, params, field);
    }

    @Override
    public List<E> transformToEntities(List<M> models, int deep, Map<String, Object> params, String... field) {
        return isEmpty(models) ? EMPTY_LIST : mapTo(models, m -> transform(m, deep, params, field));
    }

    @Override
    public List<E> transformToEntities(List<M> models, Map<String, Object> params, String... field) {
        return transformToEntities(models, FIRST_DEEP, params, field);
    }

}
