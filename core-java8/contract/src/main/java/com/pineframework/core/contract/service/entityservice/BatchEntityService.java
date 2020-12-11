package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.flat.BatchRepository;
import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.BatchService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;

import static com.pineframework.core.datamodel.utils.ObjectsUtils.areEquivalence;
import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static com.pineframework.core.helper.CollectionUtils.subtract;

/**
 * Business logic layer to support batch operation on flat data structure.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
 * @param <T> transformer
 * @param <R> data access object (DAO/Repository)
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchEntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, E, M, B>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>>
        extends EntityService<I, E, M, B, T, R>, AroundServiceOperation<I, E, M>, BatchService<I, M> {

    default void beforeBatchSave(E[] entities, M[] models) {
        for (int i = 0; i < entities.length; i++)
            beforeSave(entities[i], models[i]);
    }

    default void afterBatchSave(E[] entities, M[] models) {
        for (int i = 0; i < entities.length; i++)
            afterSave(entities[i], models[i]);
    }

    default void beforeBatchUpdate(E[] entities, M[] models) {
        for (int i = 0; i < entities.length; i++)
            beforeUpdate(entities[i], models[i]);
    }

    default void afterBatchUpdate(E[] entities, M[] theLast) {
        for (int i = 0; i < entities.length; i++)
            afterUpdate(entities[i], theLast[i]);
    }

    default void beforeBatchDelete(M[] models) {
        Arrays.stream(models)
                .forEach(this::beforeDelete);
    }

    default void afterBatchDelete(M[] models) {
        Arrays.stream(models)
                .forEach(this::afterDelete);
    }

    default void beforeBatchOperations(E[] entities, M[] models) {
    }

    default void afterBatchOperations(E[] entities, M[] models) {
    }

    @Override
    default I[] batchSave(M[] models) {
        E[] entities = getTransformer().transform(models);

        beforeBatchSave(entities, models);
        getRepository().save(entities);
        afterBatchSave(entities, models);

        return mapTo(entities, e -> e.getId(), getIdentityType());
    }

    @Override
    default void batchUpdate(M[] models) {
        E[] entities = getRepository().find(mapTo(models, m -> m.getId(), getIdentityType()));
        M[] theLast = getTransformer().transform(entities);
        beforeBatchUpdate(entities, models);
        batch(models, m -> getRepository().findById(m.getId()).ifPresent(e -> getTransformer()
                .transform(m, e)));
        afterBatchUpdate(entities, theLast);
    }

    @Override
    default void batchDelete(I[] identities) {
        M[] models = getTransformer().transform(getRepository().find(identities));

        beforeBatchDelete(models);
        getRepository().delete(identities);
        afterBatchDelete(models);
    }

    @Override
    default I[] batchOperations(M[] models, I[] identities) {
        E[] entities = getRepository().find(identities);
        beforeBatchOperations(entities, models);

        E[] deletedEntities = subtract(entities, models, (e, m) -> areEquivalence(e, m), getPersistenceType());
        batchDelete(mapTo(deletedEntities, e -> e.getId(), getIdentityType()));
        batchUpdate(intersection(models, entities, (m, e) -> areEquivalence(m, e), getTransientType()));
        I[] addedIds = batchSave(subtract(models, entities, (m, e) -> areEquivalence(m, e), getPersistenceType()));

        afterBatchOperations(entities, models);
        return addedIds;
    }

    @Override
    default void batch(M[] models, Consumer<M> operation) {
        int batchSize = getRepository().getImpl().getBatchSize();
        for (int i = 0; i < models.length; i++) {
            if (i > 0 && i % batchSize == 0) {
                getRepository().flush();
                getRepository().clear();
            }
            operation.accept(models[i]);
        }
    }
}
