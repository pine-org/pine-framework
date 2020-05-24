package com.pineframework.core.contract.service;

import com.pineframework.core.contract.log.Loggable;
import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.transformer.FlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import io.vavr.control.Try;

import java.io.Serializable;

public interface BusinessService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        T extends FlatTransformer<I, M, E>> extends Loggable {
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
