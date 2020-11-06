package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * Business logic layer to support operation for only one type.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient builder
 * @param <T> transformer
 * @param <R> repository
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface EntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, E, M, B>,
        R extends FlatRepository<I, E>>
        extends BusinessService<I, E, M, B, T, R> {

}
