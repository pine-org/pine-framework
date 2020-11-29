package com.pineframework.core.datamodel.sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class AscSort<E> extends AbstractSort<E> {

    @Override
    public Order toOrder(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        return cb.asc(root.get(getField()));
    }
}
