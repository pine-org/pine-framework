package com.pineframework.core.contract.config;

/**
 * Create a transactional bean programmatically.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TransactionalBeanFactory {
    /**
     * Factory method to create a transactional proxy of bean.
     *
     * @param bean an object should be transactional
     * @param type type of return object
     * @param <T>  bean type
     * @param <E>  return type
     * @return a proxy of bean that is transactional
     */
    <T, E> E create(T bean, Class<E> type);
}