package com.pineframework.core.datamodel.model.select;

import com.pineframework.core.datamodel.model.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

public class SelectByFilter<E> implements Select<E, E> {

    private Class<E> type;

    private Filter<E>[] filter;

    public SelectByFilter(Class<E> type, Filter<E>... filter) {
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
    public Class<E> getModelType() {
        return type;
    }

    @Override
    public BiFunction<Root<E>, CriteriaBuilder, Selection<E>> getSelection() {
        return (root, cb) -> root;
    }
}
