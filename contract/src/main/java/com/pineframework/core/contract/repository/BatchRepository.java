package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.List;

/**
 * @param <I> <description>id</description>
 * @param <E> <description>entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchRepository<I extends Serializable, E extends FlatPersistence<I>> extends Repository<I, E> {

    default List<I> batchSave(List<E> entities) {
        return getImpl().batchSave(entities, getType());
    }

    default void batchUpdate(List<E> entities) {
        for (int i = 0; i < entities.size(); i++) {

            if (i > 0 && i % getImpl().getBatchSize() == 0) {
                getImpl().getStorageSession().flush();
                getImpl().getStorageSession().clear();
            }

            getImpl().getStorageSession().merge(entities.get(i));

        }
    }

    default void batchDelete(List<I> identities) {
        getImpl().batchDelete(identities, getType());
    }
}