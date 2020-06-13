package com.pineframework.core.contract.config;

public interface TransactionalBeanFactory {
    <T, E> E create(T service, Class<E> type);
}
