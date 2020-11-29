package com.pineframework.core.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class ObjectRegistry<T> {

    private final Map<String, T> map = new HashMap<>();

    public ObjectRegistry<T> register(String name, T t) {
        map.put(name, t);
        return this;
    }

    public T lookup(String name) {
        return map.getOrDefault(name, null);
    }

    public T lookupOrElseGet(String name, Supplier<T> supplier) {
        return map.containsKey(name) ? map.get(name) : supplier.get();
    }

}
