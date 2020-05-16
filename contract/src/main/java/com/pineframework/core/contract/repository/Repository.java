package com.pineframework.core.contract.repository;

public interface Repository<I, E> {

    Class<E> getType();

    <R extends FlatRepository> R getImpl();
}