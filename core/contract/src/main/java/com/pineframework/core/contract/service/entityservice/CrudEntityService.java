package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.exception.NotFoundDataException;
import com.pineframework.core.datamodel.exception.NotSameVersionException;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;
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
        Optional<E> entity = getRepository().findById(id);

        if (!entity.isPresent())
            return empty();

        M model = getTransformer().transform(entity.get());
        return ofNullable(model);
    }

    @Override
    default void update(M m) {
        E entity = getRepository().findById(m.getId()).orElseGet(() -> createEmptyPersistenceObject());

        if (!Objects.equals(m.getVersion(), entity.getVersion()))
            throw new NotSameVersionException();

        M theLast = getTransformer().transform(entity);

        beforeUpdate(entity, m);
        getTransformer().transform(m, entity);
        getRepository().flush();
        afterUpdate(entity, theLast);
    }

    @Override
    default void delete(I id) {
        Optional<E> entity = getRepository().findById(id);

        if (!entity.isPresent())
            throw new NotFoundDataException(getTransientType().getClass().getSimpleName());

        M m = getTransformer().transform(entity.get());

        beforeDelete(m);
        getRepository().delete(id);
        afterDelete(m);
    }
}
