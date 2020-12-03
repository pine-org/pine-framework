package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.exception.NotFoundDataByIdException;
import com.pineframework.core.datamodel.exception.NotSameVersionException;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Optional;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static java.util.Optional.ofNullable;


/**
 * Business logic layer to support CRUD operation on flat data structure.
 *
 * @param <I> identity
 * @param <E> persistable object
 * @param <M> transient object
 * @param <B> transient builder
 * @param <T> object transformer
 * @param <R> repository
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudEntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, E, M, B>,
        R extends CrudRepository<I, E>>
        extends EntityService<I, E, M, B, T, R>, AroundServiceOperation<I, E, M>, CrudService<I, M> {

    @SuppressWarnings("unchecked")
    @Override
    default Optional<I> save(M m) {

        E entity = getTransformer().transform(m);

        beforeSave(entity, m);
        getRepository().save(entity);
        afterSave(entity, m);

        return ofNullable(entity.getId());
    }

    @Override
    default Optional<M> findByIdErrorProne(I id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundDataByIdException(getTransientType().getSimpleName(), id));

        return ofNullable(getTransformer().transform(entity));
    }

    @Override
    default Optional<M> findById(I id) {
        Optional<E> entity = getRepository().findById(id);
        if (!entity.isPresent())
            return Optional.empty();

        return ofNullable(getTransformer().transform(entity.get()));
    }

    @Override
    default void update(I id, M m) {
        getRepository().findById(m.getId()).ifPresent(e ->
                Match(m.getVersion()).of(
                        Case($(e.getVersion()), o -> run(() -> {
                            M theLast = getTransformer().transform(e);
                            beforeUpdate(e, m);
                            getTransformer().transform(m, e);
                            getRepository().flush();
                            afterUpdate(e, theLast);
                        })),
                        Case($(), o -> {
                            throw new NotSameVersionException();
                        })
                ));
    }

    @SuppressWarnings("unchecked")
    @Override
    default void delete(I id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundDataByIdException(getTransientType().getSimpleName(), id));

        M model = getTransformer().transform(entity);

        beforeDelete(model);
        getRepository().delete(id);
        afterDelete(model);
    }
}
