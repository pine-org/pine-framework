package com.pineframework.core.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ObjectRegistry<T> {

    private Map<String, T> map = new HashMap<>();

    public ObjectRegistry register(String name, T t) {
        map.put(name, t);
        return this;
    }

    public T lookup(String name) {
        return map.containsKey(name) ? map.get(name) : null;
    }

    public T lookupOrElseGet(String name, Supplier<T> supplier) {
        return map.containsKey(name) ? map.get(name) : supplier.get();
    }

}
