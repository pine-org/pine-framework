package com.pineframework.core.contract.repository;

import com.pineframework.core.contract.query.Column;
import com.pineframework.core.contract.query.Specification;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface FlatRepository {

    EntityManager getStorageSession();

    int getBatchSize();

    /**
     * Save operation
     *
     * @param e
     * @param c
     * @param <I> <description>id</description>
     * @param <E> <description>e</description>
     * @return <description>persisted e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> save(E e, Class<E> c);

    /**
     * @param id
     * @param c
     * @param <I> <description>id</description>
     * @param <E> <description>e</description>
     * @return <description>persisted e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findById(I id, Class<E> c);

    /**
     * Physically delete
     *
     * @param id
     * @param c
     * @param <I> <description>id</description>
     * @param <E> <description>e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> void delete(I id, Class<E> c);

    /**
     * Select all records in a list
     *
     * @param c
     * @param <I> <description>id</description>
     * @param <E> <description>e</description>
     * @return <description>list of e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<E> findAll(Class<E> c);

    /**
     * Select records with where clause {@link Specification}
     *
     * @param condition
     * @param c
     * @param <I>       <description>id</description>
     * @param <E>       <description>e</description>
     * @return <description>list of e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(Specification<E> condition, Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>, M> List<M> find(Specification<E> specification,
                                                                           Class<E> e,
                                                                           Class<M> m,
                                                                           Column<E> column);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(Specification<E> condition,
                                                                        int first,
                                                                        int max,
                                                                        Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(int first,
                                                                        int max,
                                                                        Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOneByField(String field,
                                                                                      Object value,
                                                                                      Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> findByField(String field,
                                                                               Object value,
                                                                               Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> Long count(Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> Long count(Specification<E> condition,
                                                                      Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> Boolean contains(Specification<E> condition,
                                                                            Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> E getReference(I id, Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> findByIdentities(I[] identities,
                                                                                    Class<E> c);

    <I extends Serializable, E extends FlatPersistence<I>, M> Optional<M> findOne(Specification<E> specification,
                                                                                  Class<E> e,
                                                                                  Class<M> m,
                                                                                  Column<E> column);

    /**
     * Select one record with where clause {@link Specification}
     *
     * @param condition
     * @param c
     * @param <I>       <description>id</description>
     * @param <E>       <description>e</description>
     * @return <description>one e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Specification<E> condition,
                                                                               Class<E> c);

    <I extends Serializable, K extends Serializable, E extends FlatPersistence<I>> List<E> findIn(String field,
                                                                                                  K[] valueList,
                                                                                                  Class<E> c);

    /**
     * save batch of entities
     *
     * @param entities
     * @param c
     * @param <I>      <description>id</description>
     * @param <E>      <description>e</description>
     * @return
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<I> batchSave(List<E> entities, Class<E> c);

    /**
     * delete batch of entities
     *
     * @param identities
     * @param c
     * @param <I>        <description>id</description>
     * @param <E>        <description>e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> void batchDelete(List<I> identities, Class<E> c);

    /**
     * Save operation
     *
     * @param e
     * @param c
     * @param <I> <description>id</description>
     * @param <E> <description>e</description>
     * @return <description>merge e</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> merge(E e, Class<E> c);

}
