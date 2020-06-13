package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface Repository<I extends Serializable, E extends FlatPersistence<I>> {

    Class<E> getType();

    JpaRepository getImpl();
}