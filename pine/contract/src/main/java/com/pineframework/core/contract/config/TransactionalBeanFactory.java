package com.pineframework.core.contract.config;

public interface TransactionalBeanFactory {
    <T, E> E createTransactionalBean(T service, Class<E> type) throws Throwable;
}
