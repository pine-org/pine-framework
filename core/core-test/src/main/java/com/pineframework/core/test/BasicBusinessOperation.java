package com.pineframework.core.test;

/**
 * Provide CRUD contraction.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <T> transformer
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BasicBusinessOperation<I, T, E> {

    E getOperator();

    I save(T data);

    T findById(I id);

    T update(I id, T data);

    void deleteById(I id);
}
