package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

public class SelectCount<E> implements Select<E, Long> {

    private final Class<E> type;

    private final Filter<E>[] filters;

    public SelectCount(Class<E> type, Filter<E>... filters) {
        this.type = type;
        this.filters = filters;
    }

    @Override
    public Filter<E>[] getFilters() {
        return filters;
    }

    @Override
    public Class<E> getEntityType() {
        return type;
    }

    @Override
    public Class<Long> getModelType() {
        return Long.class;
    }

    @Override
    public BiFunction<Root<E>, CriteriaBuilder, Selection<Long>> getSelection() {
        return (root, cb) -> cb.count(root);
    }
}
