package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.TreePersistence;
import com.pineframework.core.datamodel.select.SelectChildren;
import com.pineframework.core.datamodel.select.SelectSubTreeAsList;
import com.pineframework.core.datamodel.select.SelectTreeAsList;

import java.io.Serializable;
import java.util.Optional;

/**
 * data access layer for tree structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TreeRepository<I extends Serializable, E extends TreePersistence<I, E>> extends FlatRepository<I, E> {

    default E[] findChildren(I id) {
        return getImpl().find(new SelectChildren<E>(getType(), id));
    }

    default E[] findTreeAsList(I id) {
        E root = getImpl().findOne(getType(), id).get();
        return getImpl().find(new SelectTreeAsList<E>(getType(), root.getPath()));
    }

    default E[] findSubTreeAsList(I id) {
        E root = getImpl().findOne(getType(), id).get();
        return getImpl().find(new SelectSubTreeAsList<E>(getType(), root.getPath()));
    }

    default Optional<E> findTree(I id) {
        return getImpl().findOne(getType(), id);
    }

}
