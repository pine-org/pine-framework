package com.pineframework.core.contract.service.entity;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.Transformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import io.vavr.control.Try;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
public interface EntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        T extends Transformer<I, M, E>> extends BusinessService {

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
