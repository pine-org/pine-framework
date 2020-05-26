package com.pineframework.core.contract.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import io.vavr.control.Try;

import java.io.Serializable;

public interface BusinessService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>> extends Loggable {
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
