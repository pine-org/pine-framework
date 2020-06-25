package com.pineframework.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public abstract class AbstractTest<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Map<String, T> storage;

    public AbstractTest(Map<String, T> storage) {
        this.storage = storage;
        init();
    }

    public AbstractTest() {
        this(new HashMap<>());
    }

    protected void init() {
    }

    protected void addToStorage(String name, T t) {
        storage.putIfAbsent(name, t);
    }

    protected void updateStorage(String name, T t) {
        storage.put(name, t);
    }

    protected final T getFromStorage(String name) {
        return storage.get(name);
    }

    protected void logInfo(Object o) {
        logger.info(format("LOG [INFO]: %s", o));
    }
}
