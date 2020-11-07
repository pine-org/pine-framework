package com.pineframework.core.test;

public interface BasicBusinessOperation<I, T, E> {

    E getOperator();

    I save(T data);

    T findById(I id);

    T update(I id, T data);

    void deleteById(I id);
}
