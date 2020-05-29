package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

public class SelectCount<E> implements Select<E, Long> {

    private Class<E> type;

    private Filter<E>[] filter;

    public SelectCount(Class<E> type, Filter<E>... filter) {
        this.type = type;
        this.filter = filter;
    }

    @Override
    public Filter<E>[] getFilter() {
        return filter;
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
