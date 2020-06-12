package com.pineframework.core.test;

import java.util.Map;

public interface BasicBusinessOperation<T, E> {

    E getOperator();

    void initData(Map<String, T> models);

    T getData(String name);

    void saveDataThenAssertIdIsNotNull(String name);

    void findAllDataWithExpectedCount(int count);

    void updateCurrentDataWith(String name);

    void deleteDataThenDecreaseCount(String name);
}
