package com.pineframework.core.cache.event;

import com.google.common.primitives.Longs;

import java.lang.ref.SoftReference;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class DelayedCacheObject implements Delayed, CacheObject, Expired {

    private final String key;

    private final String id;

    private final SoftReference<Object> data;

    private final RowOperation operation;

    private long expiryTime;

    public DelayedCacheObject(String key,
                              String id,
                              SoftReference<Object> data,
                              RowOperation operation,
                              long expiryTime) {
        this.key = key;
        this.id = id;
        this.data = data;
        this.operation = operation;
        this.expiryTime = expiryTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Longs.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
    }

    public String getKey() {
        return key;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public SoftReference<Object> getValue() {
        return data;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public RowOperation getOperation() {
        return operation;
    }

    @Override
    public long getExpiryTime() {
        return expiryTime;
    }

    public void startFromNow() {
        this.expiryTime += System.currentTimeMillis();
    }
}