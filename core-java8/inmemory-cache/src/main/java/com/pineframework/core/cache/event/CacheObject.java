package com.pineframework.core.cache.event;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CacheObject {
    String getKey();

    Object getId();

    Object getValue();

    String getClassName();

    RowOperation getOperation();
}
