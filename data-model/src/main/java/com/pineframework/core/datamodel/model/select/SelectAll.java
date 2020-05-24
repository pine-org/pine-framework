package com.pineframework.core.datamodel.model.select;

import com.pineframework.core.datamodel.model.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

public class SelectAll<E> implements Select<E, E> {

    private Class<E> type;

    public SelectAll(Class<E> type) {
        this.type = type;
    }

    @Override
    public Filter<E>[] getFilter() {
        return new Filter[0];
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