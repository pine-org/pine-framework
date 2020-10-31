package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;

public class SelectChildren<E> implements Select<E, E> {

    private final Class<E> type;

    private final Serializable id;

    public SelectChildren(Class<E> type, Serializable id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Filter<E>[] getFilters() {
        Filter<E> filterNullParent = (root, query, cb) -> cb.isNull(root.get("parent"));
        Filter<E> filterByParent = (root, query, cb) -> cb.equal(root.get("parent"), id);
        Filter<E> filter = isNull(id) ? filterNullParent : filterByParent;
        return new Filter[]{filter};
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
