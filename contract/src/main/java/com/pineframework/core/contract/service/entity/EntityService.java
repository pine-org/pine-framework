package com.pineframework.core.contract.service.entity;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.transformer.Transformer;
import com.pineframework.core.datastructure.model.FlatTransient;
import com.pineframework.core.datastructure.persistence.FlatPersistence;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
public interface EntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        T extends Transformer<I, M, E>> {

    Class<M> getTransientType();

    Class<E> getPersistenceType();

    R getRepository();

    T getTransformer();

    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

    default E createEmptyPersistence() {
        return Try.of(() -> getPersistenceType().newInstance()).get();
    }

    default M createEmptyModel() {
        return Try.of(() -> getTransientType().newInstance()).get();
    }
}
