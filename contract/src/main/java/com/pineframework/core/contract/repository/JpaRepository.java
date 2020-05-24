package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.model.paging.Pageable;
import com.pineframework.core.datamodel.model.select.Select;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface JpaRepository {

    EntityManager getStorageSession();

    int getBatchSize();

    <I extends Serializable, E extends FlatPersistence<I>> void save(Class<E> c, E... entities);

    <I extends Serializable, E extends FlatPersistence<I>> void delete(Class<E> c, I... identities);

    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Class<E> c, I id);

    <I extends Serializable, E extends FlatPersistence<I>, M> Optional<M> findOne(Select<E, M> select);

    <I extends Serializable, E extends FlatPersistence<I>, M> M[] find(Select<E, M> select);

    <I extends Serializable, E extends FlatPersistence<I>> E[] paging(Class<E> c, Pageable page);

}
