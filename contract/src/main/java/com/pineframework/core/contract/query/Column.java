package com.pineframework.core.contract.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 * get result of query with custom column
 * <pre>
 *     Example:
 *
 *     &#064;repository.find({@link Specification},
 *                 javabean.class,
 *                 (root, cb) -> new Selection[]{root.get("name")});
 * </pre>
 *
 * @param <E> <description>type of entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 * @see Specification
 */

@FunctionalInterface
public interface Column<E> {

    /**
     * @param root <description>criteria root</description>
     * @param cb   <description>{@code CriteriaBuilder}></description>
     * @return <description>array of entity field</description>
     */
    Selection<E>[] get(Root<E> root, CriteriaBuilder cb);

}