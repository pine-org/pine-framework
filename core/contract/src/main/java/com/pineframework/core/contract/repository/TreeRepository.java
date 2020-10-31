package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.TreePersistence;
import com.pineframework.core.datamodel.select.SelectChildren;
import com.pineframework.core.datamodel.select.SelectSubTreeAsList;
import com.pineframework.core.datamodel.select.SelectTreeAsList;

import java.io.Serializable;
import java.util.Optional;

import static com.pineframework.core.helper.CollectionUtils.createArray;

/**
 * Data access layer to support tree data structure.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TreeRepository<I extends Serializable, E extends TreePersistence<I, E>> extends FlatRepository<I, E> {

    /**
     * Get level one children of a node.
     *
     * @param id node identity
     * @return node array
     */
    @SuppressWarnings("Convert2Diamond")
    default E[] findChildren(I id) {
        return getImpl().find(new SelectChildren<E>(getType(), id));
    }

    /**
     * Get all tree nodes in one dimensional array.
     *
     * @param id node identity
     * @return node array
     */
    @SuppressWarnings("Convert2Diamond")
    default E[] findTreeAsList(I id) {
        Optional<E> root = getImpl().findOne(getType(), id);
        if (root.isPresent())
            return getImpl().find(new SelectTreeAsList<E>(getType(), root.get().getPath()));

        return createArray(getType(), 0);
    }

    /**
     * Get all children nodes in one dimensional array.
     *
     * @param id node identity
     * @return node array
     */
    @SuppressWarnings("Convert2Diamond")
    default E[] findSubTreeAsList(I id) {
        Optional<E> root = getImpl().findOne(getType(), id);
        if (root.isPresent())
            return getImpl().find(new SelectSubTreeAsList<E>(getType(), root.get().getPath()));

        return createArray(getType(), 0);
    }

    /**
     * Get all tree nodes in tree structure.
     *
     * @param id node identity
     * @return root node
     */
    default Optional<E> findTree(I id) {
        return getImpl().findOne(getType(), id);
    }

}
