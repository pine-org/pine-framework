package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.exception.NotSameVersionException;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudEntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>,
        R extends CrudRepository<I, E>>
        extends EntityService<I, E, M, B, T, R>, AroundServiceOperation<I, E, M>, CrudService<I, M> {

    @Override
    default Optional<I> save(M m) {

        E entity = getTransformer().transform(m);

        beforeSave(entity, m);
        getRepository().save(entity);
        afterSave(entity, m);

        return ofNullable(entity.getId());
    }

    @Override
    default Optional<M> findById(I id) {
        E entity = getRepository().findById(id).orElseGet(() -> createEmptyPersistenceObject());
        M m = getTransformer().transform(entity);
        return ofNullable(m);
    }

    @Override
    default void update(M m) {
        E entity = getRepository().findById(m.getId()).orElseGet(() -> createEmptyPersistenceObject());

        if (!Objects.equals(m.getVersion(), entity.getVersion()))
            throw new NotSameVersionException();

        M oldData = getTransformer().transform(entity);

        beforeUpdate(entity, m);
        getTransformer().transform(m, entity);
        afterUpdate(entity, m, oldData);
    }

    @Override
    default void delete(I id) {
        E entity = getRepository().findById(id).orElseGet(() -> createEmptyPersistenceObject());
        M m = getTransformer().transform(entity);

        beforeDelete(m);
        getRepository().delete(id);
        afterDelete(m);
    }
}
