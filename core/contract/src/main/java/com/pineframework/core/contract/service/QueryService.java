package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.paging.Page;

import java.util.Map;
import java.util.Optional;

/**
 * Business logic layer to support read only operation flat data structure.
 *
 * @param <I> identity
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface QueryService<I, M> {

    /**
     * Get all data
     *
     * @return array of transient object
     */
    M[] findAll();

    /**
     * Get a page of data
     *
     * @param page page
     * @return page
     */
    Optional<Page> findByPage(Page page, Map<String, Object> params);

    /**
     * Count the records of data
     *
     * @param filters where clause
     * @return long number
     */
    @SuppressWarnings("rawtypes")
    long count(Filter... filters);

    /**
     * Get data by filters
     *
     * @param filters where clause
     * @return array of transient object
     */
    @SuppressWarnings("rawtypes")
    M[] findByFilter(Filter... filters);

    /**
     * Find data by example
     *
     * @param m transient object
     * @return a transient object
     */
    Optional<M> findByModel(M m);
}
