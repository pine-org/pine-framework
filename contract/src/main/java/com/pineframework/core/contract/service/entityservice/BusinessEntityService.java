package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.transformer.Transformer;
import com.pineframework.core.datastructure.model.FlatTransient;
import com.pineframework.core.datastructure.persistence.FlatPersistence;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Transactional
public interface BusinessEntityService<
        I extends Serializable,
        M extends FlatTransient<I>,
        P extends FlatPersistence<I>,
        R extends Repository<I, P, R>,
        T extends Transformer<I, M, P>> {

    Class<M> getModelType();

    Class<P> getPersistenceType();

    R getRepository();

    /**
     * When there are multi transformer for a specific type, it must be add them to a map.
     *
     * @return collections of transformer as a map
     */
    default Map<String, Transformer<I, M, P>> getTransformerMap() {
        return Collections.emptyMap();
    }

    T getTransformer();

    default Transformer<I, M, P> getTransformer(String name) {
        return getTransformerMap().containsKey(name) ? getTransformerMap().get(name) : getTransformer();
    }

    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

    default P createEmptyPersistence() {
        return Try.of(() -> getPersistenceType().newInstance()).get();
    }

    default M createEmptyModel() {
        return Try.of(() -> getModelType().newInstance()).get();
    }
}
