package com.pineframework.core.business.service;

import com.pineframework.core.datastructure.model.AbstractTransient;
import com.pineframework.core.datastructure.persistence.Persistence;
import com.pineframework.core.datastructure.transformer.Transformer;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Transactional
public interface BusinessService<
        I extends Serializable,
        V extends Serializable,
        M extends AbstractTransient<I, V>,
        P extends Persistence<I, V>,
        R extends Repository<I, P>,
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
