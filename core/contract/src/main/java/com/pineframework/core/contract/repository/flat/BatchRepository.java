package com.pineframework.core.contract.repository.flat;

import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * Data access layer to support batch operation on flat data structure.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchRepository<I extends Serializable, E extends FlatPersistence<I>>
        extends FlatRepository<I, E> {

}
