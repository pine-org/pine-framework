package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SelectByDifferentReturnType<E, M> implements Select<E, M> {

    private Class<E> entity;

    private Class<M> returnType;

    private String[] columns;

    public SelectByDifferentReturnType(Class<E> entity, Class<M> returnType, String... columns) {
        this.entity = entity;
        this.returnType = returnType;
        this.columns = columns;
    }

    @Override
    public Filter<E>[] getFilter() {
        return new Filter[0];
    }

    @Override
    public Class<E> getEntityType() {
        return entity;
    }

    @Override
    public Class<M> getModelType() {
        return returnType;
    }

    @Override
    public BiFunction<Root<E>, CriteriaBuilder, Selection<M>> getSelection() {
        return (root, cb) -> cb.construct(returnType, getColumns(root));
    }

    public Selection[] getColumns(Root<E> root) {
        return Stream.of(columns).map(column -> root.get(column)).toArray(Selection[]::new);
    }
}
