package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

public class SelectByFilter<E> implements Select<E, E> {

    private final Class<E> type;

    private final Filter<E>[] filters;

    public SelectByFilter(Class<E> type, Filter<E>... filters) {
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
    public Class<E> getModelType() {
        return type;
    }

    @Override
    public BiFunction<Root<E>, CriteriaBuilder, Selection<E>> getSelection() {
        return (root, cb) -> root;
    }
}
