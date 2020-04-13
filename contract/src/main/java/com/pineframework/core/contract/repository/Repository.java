package com.pineframework.core.contract.repository;

public interface Repository<I, E, R> {

    Class<E> getType();

    R getImplementation();
}