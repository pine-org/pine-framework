package com.pineframework.core.cache.template;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface HashTemplate<T> {

    void save(T value);

    void remove(String key, String id);

    void remove(String key);

    Optional<SoftReference<Object>> get(String key, String id);

    Map<Object, SoftReference<Object>> get(String key);

    void clear();

    void truncate(String key);

    long keySize();

    long size();

    long size(String key);
}