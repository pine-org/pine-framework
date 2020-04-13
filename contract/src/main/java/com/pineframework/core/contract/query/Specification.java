package com.pineframework.core.contract.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * set condition for query
 * <pre>
 *     Example:
 *
 *     &#064;repository.find((root, query, cb) -> cb.equal(root.get("id"), 1);
 * </pre>
 *
 * @param <E> <description>type of entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@FunctionalInterface
public interface Specification<E> {

    /**
     * generate where clause
     *
     * @param root  <description>criteria root</description>
     * @param query <description>{@code CriteriaQuery}</description>
     * @param cb    <description>{@code CriteriaBuilder}></description>
     * @return <description>where clause</description>
     */
    Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb);

}