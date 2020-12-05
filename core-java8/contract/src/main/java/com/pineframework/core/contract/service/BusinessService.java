package com.pineframework.core.contract.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import io.vavr.control.Try;

import java.io.Serializable;

/**
 * Business logic layer provides operations on transient objects.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient builder
 * @param <T> transformer
 * @param <R> repository
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BusinessService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, E, M, B>,
        R extends FlatRepository<I, E>> extends Loggable {

    Class<M> getTransientType();

    Class<E> getPersistenceType();

    R getRepository();

    T getTransformer();

    default E createEmptyPersistenceObject() {
        return Try.of(() -> getPersistenceType().newInstance()).get();
    }

    default M createEmptyModelObject() {
        return Try.of(() -> getTransientType().newInstance()).get();
    }

}
