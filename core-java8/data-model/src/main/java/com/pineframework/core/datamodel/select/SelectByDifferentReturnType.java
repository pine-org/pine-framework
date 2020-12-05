package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SelectByDifferentReturnType<E, M> implements Select<E, M> {

    private final Class<E> entity;

    private final Class<M> returnType;

    private final String[] columns;

    private final Filter<E>[] filters;

    public SelectByDifferentReturnType(Class<E> entity, Class<M> returnType, Filter<E>[] filters, String... columns) {
        this.entity = entity;
        this.returnType = returnType;
        this.filters = Objects.isNull(filters) ? new Filter[0] : filters;
        this.columns = columns;
    }

    @Override
    public Filter<E>[] getFilters() {
        return filters;
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
