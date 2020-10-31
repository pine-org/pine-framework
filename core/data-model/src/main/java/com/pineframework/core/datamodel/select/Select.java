package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Select<E, M> {

    default Filter<E>[] getFilters() {
        return new Filter[0];
    }

    Class<E> getEntityType();

    Class<M> getModelType();

    BiFunction<Root<E>, CriteriaBuilder, Selection<M>> getSelection();

    default BiConsumer<CriteriaQuery<M>, Function<Filter<E>, Predicate>> getWhereClause() {
        if (getFilters().length == 0)
            return (cq, mapper) -> {
            };

        return (cq, mapper) -> cq.where(Stream.of(getFilters()).map(mapper).toArray(Predicate[]::new));
    }
}
