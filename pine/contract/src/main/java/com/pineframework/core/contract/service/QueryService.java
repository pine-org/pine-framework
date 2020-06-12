package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.paging.Page;

import java.util.Optional;

public interface QueryService<I, M> {
    M[] findAll();

    Page findByPage(Page page);

    long count(Filter... filters);

    M[] findByFilter(Filter... filters);

    Optional<M> findByModel(M m);
}
