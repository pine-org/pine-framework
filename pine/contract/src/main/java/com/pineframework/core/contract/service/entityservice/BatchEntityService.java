package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.BatchService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.function.Consumer;

import static com.pineframework.core.datamodel.utils.ObjectsUtils.areEquivalence;
import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static com.pineframework.core.helper.CollectionUtils.subtract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchEntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>>
        extends EntityService<I, M, E, R, B, T>, AroundServiceOperation<I, M, E>, BatchService<I, M> {

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

    default void afterBatchUpdate(E[] entities, M[] models, M[] oldData) {
        for (int i = 0; i < entities.length; i++)
            afterUpdate(entities[i], models[i], oldData[i]);
    }

    default void beforeBatchDelete(M[] models) {
        for (int i = 0; i < models.length; i++)
            beforeDelete(models[i]);
    }

    default void afterBatchDelete(M[] models) {
        for (int i = 0; i < models.length; i++)
            afterDelete(models[i]);
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

        return mapTo(entities, e -> e.getId(), Long.class);
    }

    @Override
    default void batchUpdate(M[] models) {
        E[] entities = getRepository().find((I[]) mapTo(models, m -> m.getId(), Long.class));
        M[] oldData = getTransformer().transform(entities);
        beforeBatchUpdate(entities, models);
        batch(models, m -> getTransformer().transform(m, getRepository().findById(m.getId()).get()));
        afterBatchUpdate(entities, models, oldData);
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
        batchDelete(mapTo(deletedEntities, e -> e.getId(), Long.class));
        batchUpdate(intersection(models, entities, (m, e) -> areEquivalence(m, e), getTransientType()));
        I[] addedIds = batchSave(subtract(models, entities, (m, e) -> areEquivalence(m, e), getPersistenceType()));

        afterBatchOperations(entities, models);
        return addedIds;
    }

    @Override
    default void batch(M[] models, Consumer<M> operation) {
        for (int i = 0; i < models.length; i++) {
            if (i > 0 && i % getRepository().getImpl().getBatchSize() == 0) {
                getRepository().getImpl().getStorageSession().flush();
                getRepository().getImpl().getStorageSession().clear();
            }
            operation.accept(models[i]);
        }
    }
}
