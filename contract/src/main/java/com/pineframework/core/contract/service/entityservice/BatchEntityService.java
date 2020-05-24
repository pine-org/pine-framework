package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.transformer.FlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.function.Consumer;

import static com.pineframework.core.datamodel.utils.ObjectsUtils.areEquivalence;
import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static com.pineframework.core.helper.CollectionUtils.subtract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Transactional
public interface BatchEntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>,
        T extends FlatTransformer<I, M, E>>
        extends EntityService<I, M, E, R, T>, AroundServiceOperation<I, M, E> {

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

    default I[] batchSave(M[] models) {
        E[] entities = getTransformer().transform(models);

        beforeBatchSave(entities, models);
        getRepository().save(entities);
        afterBatchSave(entities, models);

        return mapTo(entities, e -> e.getId(), Long.class);
    }

    default void batchUpdate(M[] models) {
        E[] entities = getRepository().findByIdentities(mapTo(models, m -> m.getId(), Long.class));
        M[] oldData = getTransformer().transform(entities);
        beforeBatchUpdate(entities, models);
        batch(models, m -> getTransformer().transform(m, getRepository().findById(m.getId()).get()));
        afterBatchUpdate(entities, models, oldData);
    }

    default void batchDelete(I[] identities) {
        M[] models = getTransformer().transform(getRepository().findByIdentities(identities));

        beforeBatchDelete(models);
        getRepository().delete(identities);
        afterBatchDelete(models);
    }

    default void batchDelete(E[] entities) {
        batchDelete((I[]) mapTo(entities, e -> e.getId(), Long.class));
    }

    default I[] batchOperations(M[] models, I[] identities) {
        E[] entities = getRepository().findByIdentities(identities);
        beforeBatchOperations(entities, models);

        batchDelete(subtract(entities, models, (e, m) -> areEquivalence(e, m), getPersistenceType()));
        batchUpdate(intersection(models, entities, (m, e) -> areEquivalence(m, e), getTransientType()));
        I[] added = batchSave(subtract(models, entities, (m, e) -> areEquivalence(m, e), getPersistenceType()));

        afterBatchOperations(entities, models);
        return added;
    }

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