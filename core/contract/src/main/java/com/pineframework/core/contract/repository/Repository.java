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

    /**
     * Apply flush concept on database.
     */
    void flush();

    /**
     * Clear ORM session.
     */
    void clear();

    /**
     * Size of a bunch of operations to execute at one step. Then it should be execute flush and clear operation.
     *
     * @return integer
     */
    int getBatchSize();

    /**
     * Save one or more persistable object.
     *
     * @param c        persistable class type
     * @param entities persistable object(s)
     * @param <I>      identity type
     * @param <E>      persistable type
     */
    <I extends Serializable, E extends FlatPersistence<I>> void save(Class<E> c, E... entities);

    /**
     * Delete one or more persistable object with its/their identity(ies).
     *
     * @param c          persistable class type
     * @param identities identity(ies)
     * @param <I>        identity type
     * @param <E>        persistable type
     */
    <I extends Serializable, E extends FlatPersistence<I>> void delete(Class<E> c, I... identities);

    /**
     * Find only one persistable object with its identity.
     *
     * @param c   persistable class type
     * @param id  identity
     * @param <I> identity type
     * @param <E> persistable type
     * @return a persistable object
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Class<E> c, I id);

    /**
     * Find only one persistable object with conditions.
     *
     * @param select where clause
     * @param <I>    identity type
     * @param <E>    persistable type
     * @return a persistable object
     */
    <I extends Serializable, E extends FlatPersistence<I>, M> Optional<M> findOne(Select<E, M> select);

    /**
     * Find at least one persistable object with conditions.
     *
     * @param select where clause
     * @param <I>    identity type
     * @param <E>    persistable type
     * @return persistable object array
     */
    <I extends Serializable, E extends FlatPersistence<I>, M> M[] find(Select<E, M> select);

    /**
     * Find at least one persistable object in page data structure.
     *
     * @param c    persistable class type
     * @param page page object
     * @param <I>  identity type
     * @param <E>  persistable type
     * @return page of data include persistable object array
     */
    <I extends Serializable, E extends FlatPersistence<I>> E[] paging(Class<E> c, Pageable page);

}
