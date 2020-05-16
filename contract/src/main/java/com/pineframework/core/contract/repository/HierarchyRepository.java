package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * data access layer for tree structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface HierarchyRepository<I extends Serializable, E extends TreePersistence<I, E>> extends Repository<I, E> {

    @Override
    TreeRepository getImpl();

    default List<E> findChildren(I id) {
        return getImpl().findChildren(id, getType());
    }

    default List<E> findListTree(I id) {
        return getImpl().findListTree(id, getType());
    }

    default List<E> findListSubTree(I id) {
        return getImpl().findListSubTree(id, getType());
    }

    default Optional<E> findTree(I id) {
        return getImpl().findTree(id, getType());
    }

}
