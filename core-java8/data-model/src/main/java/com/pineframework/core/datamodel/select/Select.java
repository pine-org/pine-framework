package com.pineframework.core.datamodel.select;

import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.sort.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
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

    default Sort<E>[] getSorts() {
        return new Sort[0];
    }

    Class<E> getEntityType();

    Class<M> getModelType();

    BiFunction<Root<E>, CriteriaBuilder, Selection<M>> getSelection();

    default BiConsumer<CriteriaQuery<M>, Function<Filter<E>, Predicate>> getPredicates() {
        if (getFilters().length == 0)
            return (cq, mapper) -> {
            };

        return (cq, mapper) -> cq.where(Stream.of(getFilters()).map(mapper).toArray(Predicate[]::new));
    }

    default BiConsumer<CriteriaQuery<M>, Function<Sort<E>, Order>> getOrders() {
        if (getSorts().length == 0)
            return (cq, mapper) -> {
            };

        return (cq, mapper) -> cq.orderBy(Stream.of(getSorts()).map(mapper).toArray(Order[]::new));
    }
}
