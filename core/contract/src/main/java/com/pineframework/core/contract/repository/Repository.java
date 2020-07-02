package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.paging.Pageable;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.datamodel.select.Select;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Repository {

    void flush();

    void clear();

    int getBatchSize();

    <I extends Serializable, E extends FlatPersistence<I>> void save(Class<E> c, E... entities);

    <I extends Serializable, E extends FlatPersistence<I>> void delete(Class<E> c, I... identities);

    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Class<E> c, I id);

    <I extends Serializable, E extends FlatPersistence<I>, M> Optional<M> findOne(Select<E, M> select);

    <I extends Serializable, E extends FlatPersistence<I>, M> M[] find(Select<E, M> select);

    <I extends Serializable, E extends FlatPersistence<I>> E[] paging(Class<E> c, Pageable page);

}
