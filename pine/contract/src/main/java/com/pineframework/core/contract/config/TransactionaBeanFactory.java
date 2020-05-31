package com.pineframework.core.contract.config;

public interface TransactionaBeanFactory {
    <T, E> E createTransactionalBean(T service, Class<E> type) throws Throwable;
}
