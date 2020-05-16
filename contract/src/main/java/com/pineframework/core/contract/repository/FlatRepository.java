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
     * @param entity
     * @param clazz
     * @param <I>    <description>id</description>
     * @param <E>    <description>entity</description>
     * @return <description>persisted entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> save(E entity, Class<E> clazz);

    /**
     * @param id
     * @param clazz
     * @param <I>   <description>id</description>
     * @param <E>   <description>entity</description>
     * @return <description>persisted entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findById(I id, Class<E> clazz);

    /**
     * Physically delete
     *
     * @param id
     * @param clazz
     * @param <I>   <description>id</description>
     * @param <E>   <description>entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> void delete(I id, Class<E> clazz);

    /**
     * Select all records in a list
     *
     * @param clazz
     * @param <I>   <description>id</description>
     * @param <E>   <description>entity</description>
     * @return <description>list of entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<E> findAll(Class<E> clazz);

    /**
     * Select records with where clause {@link Specification}
     *
     * @param condition
     * @param clazz
     * @param <I>       <description>id</description>
     * @param <E>       <description>entity</description>
     * @return <description>list of entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(Specification<E> condition, Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>, V> List<V> find(Specification<E> specification,
                                                                           Class<E> entity,
                                                                           Class<V> vo,
                                                                           Column<E> column);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(Specification<E> condition,
                                                                        int first,
                                                                        int max,
                                                                        Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> find(int first,
                                                                        int max,
                                                                        Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOneByField(String field,
                                                                                      Object value,
                                                                                      Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> findByField(String field,
                                                                               Object value,
                                                                               Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> Long count(Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> Long count(Specification<E> condition,
                                                                      Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> Boolean contains(Specification<E> condition,
                                                                            Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> E getReference(I id, Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>> List<E> findByIdentities(I[] identities,
                                                                                    Class<E> clazz);

    <I extends Serializable, E extends FlatPersistence<I>, V> Optional<V> findOne(Specification<E> specification,
                                                                                  Class<E> entity,
                                                                                  Class<V> vo,
                                                                                  Column<E> column);

    /**
     * Select one record with where clause {@link Specification}
     *
     * @param condition
     * @param clazz
     * @param <I>       <description>id</description>
     * @param <E>       <description>entity</description>
     * @return <description>one entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Specification<E> condition,
                                                                               Class<E> clazz);

    <I extends Serializable, K extends Serializable, E extends FlatPersistence<I>> List<E> findIn(String field,
                                                                                                  K[] valueList,
                                                                                                  Class<E> clazz);

    /**
     * save batch of entities
     *
     * @param entities
     * @param clazz
     * @param <I>      <description>id</description>
     * @param <E>      <description>entity</description>
     * @return
     */
    <I extends Serializable, E extends FlatPersistence<I>> List<I> batchSave(List<E> entities, Class<E> clazz);

    /**
     * delete batch of entities
     *
     * @param identities
     * @param clazz
     * @param <I>        <description>id</description>
     * @param <E>        <description>entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> void batchDelete(List<I> identities, Class<E> clazz);

    /**
     * Save operation
     *
     * @param entity
     * @param clazz
     * @param <I>    <description>id</description>
     * @param <E>    <description>entity</description>
     * @return <description>merge entity</description>
     */
    <I extends Serializable, E extends FlatPersistence<I>> Optional<E> merge(E entity, Class<E> clazz);

}
