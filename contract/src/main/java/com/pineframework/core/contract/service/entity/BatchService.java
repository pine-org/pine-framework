package com.pineframework.core.contract.service.entity;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.transformer.Transformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.datamodel.utils.ObjectsUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.listOf;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static com.pineframework.core.helper.CollectionUtils.subtract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Transactional
public interface BatchService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>,
        T extends Transformer<I, M, E>>
        extends EntityService<I, M, E, R, T>, AroundServiceOperation<I, M, E> {

    default void beforeBatchSave(List<M> models) {
    }

    default List<I> batchSave(List<M> models) {
        beforeBatchSave(models);

        int size = models.size();

        List<E> entities = getTransformer().transformModelsToEntities(models);

        for (int i = 0; i < size; i++)
            beforeSave(entities.get(i), models.get(i));

        List<I> identities = getRepository().batchSave(entities);

        for (int i = 0; i < size; i++)
            afterSave(entities.get(i), models.get(i));

        afterBatchSave(models);

        return identities;
    }

    default void afterBatchSave(List<M> models) {
    }

    default void beforeBatchUpdate(List<M> models) {
    }

    default void batchUpdate(List<M> models) {
        beforeBatchUpdate(models);

        int size = models.size();

        List<I> identities = mapTo(models, m -> m.getId());
        List<M> oldData = getTransformer().transformEntitiesToModels(getRepository().findByIdentities(identities));
        List<E> entities = getTransformer().transformModelsToEntities(models);

        for (int i = 0; i < size; i++) beforeUpdate(entities.get(i), models.get(i));

        getRepository().batchUpdate(entities);

        for (int i = 0; i < size; i++) afterUpdate(entities.get(i), models.get(i), oldData.get(i));

        afterBatchUpdate(models);
    }

    default void afterBatchUpdate(List<M> models) {
    }

    default void beforeBatchDelete(List<M> models) {
    }

    default void batchDelete(List<I> identities) {
        List<M> models = getTransformer().transformEntitiesToModels(getRepository().findByIdentities(identities));

        beforeBatchDelete(models);

        for (M model : models)
            beforeDelete(model);

        getRepository().batchDelete(identities);

        for (M model : models)
            afterDelete(model);

        afterBatchDelete(models);
    }

    default void afterBatchDelete(List<M> models) {
    }

    default void beforeBatchOperations(List<M> models, List<E> entities) {
    }

    default List<I> batchOperations(List<M> models, List<I> identities) {
        List<E> entities = getRepository().findByIdentities(identities);
        beforeBatchOperations(models, entities);

        batchDelete(listOf(mapTo(subtract(entities, models, (e, m) -> ObjectsUtils.isEquals(e, m)), e -> e.getId())));
        batchUpdate(listOf(intersection(models, entities, (m, e) -> ObjectsUtils.isEquals(m, e))));
        List<I> ids = batchSave(listOf(subtract(models, entities, (m, e) -> ObjectsUtils.isEquals(m, e))));

        afterBatchOperations(models, entities);
        return ids;
    }

    default void afterBatchOperations(List<M> models, List<E> entities) {
    }
}
