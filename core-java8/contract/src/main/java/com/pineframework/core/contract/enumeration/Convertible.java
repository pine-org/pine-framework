package com.pineframework.core.contract.enumeration;

/**
 * It should be used when an enum is converted to database column and vice versa.
 *
 * @param <T> database column type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Convertible<T> {
    /**
     * @return database value
     */
    T getValue();
}