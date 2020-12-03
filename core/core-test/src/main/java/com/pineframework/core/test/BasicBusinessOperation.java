package com.pineframework.core.test;

import java.util.Optional;

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

    Optional<I> save(T data);

    Optional<T> findById(I id);

    void update(I id, T data);

    void deleteById(I id);
}
