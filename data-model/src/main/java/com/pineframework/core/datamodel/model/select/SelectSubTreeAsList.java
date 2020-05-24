package com.pineframework.core.datamodel.model.select;

import com.pineframework.core.datamodel.model.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;

import static java.lang.String.format;

public class SelectSubTreeAsList<E> implements Select<E, E> {

    private Class<E> type;

    private String path;

    public SelectSubTreeAsList(Class<E> type, String path) {
        this.type = type;
        this.path = path;
    }

    @Override
    public Filter<E>[] getFilter() {
        Filter<E> filter = (root, query, cb) -> cb.and(
                cb.like(root.get("path"), format("%s%s", path, "%")),
                cb.notEqual(root.get("path"), path)
        );
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
