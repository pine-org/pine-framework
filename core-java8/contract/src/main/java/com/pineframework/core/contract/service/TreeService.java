package com.pineframework.core.contract.service;

import java.util.Optional;

/**
 * Business logic layer to support operation on tree data structure.
 *
 * @param <I> identity
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TreeService<I, M> {

    /**
     * Get first level children by parent identity
     *
     * @param id parent identity
     * @return array of transient object
     */
    M[] findChildren(I id);

    /**
     * Get tree by parent identity
     *
     * @param id parent identity
     * @return array of transient object
     */
    M[] findTreeAsList(I id);

    /**
     * Get all levels children by parent identity
     *
     * @param id parent identity
     * @return array of transient object
     */
    M[] findSubTreeAsList(I id);

    /**
     * Get whole tree structure
     *
     * @param id parent identity
     * @return root as a transient object
     */
    Optional<M> findTree(I id);
}
