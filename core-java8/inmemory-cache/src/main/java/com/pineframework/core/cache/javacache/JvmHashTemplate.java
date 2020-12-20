package com.pineframework.core.cache.javacache;

import com.pineframework.core.cache.event.DelayedCacheObject;
import com.pineframework.core.cache.template.HashTemplate;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class JvmHashTemplate implements HashTemplate<DelayedCacheObject> {

    private final ConcurrentHashMap<String, Map<Object, SoftReference<Object>>> storage = new ConcurrentHashMap<>();

    private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();

    public JvmHashTemplate() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();

                    if (delayedCacheObject.getExpiryTime() > -1L) {
                        storage.remove(delayedCacheObject.getKey(), delayedCacheObject.getValue());
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public void save(DelayedCacheObject object) {

        if (Objects.isNull(object.getKey()) || Objects.isNull(object.getId()))
            return;

        if (Objects.isNull(object.getValue())) {
            storage.get(object.getKey()).remove(object.getId());
            return;
        }

        if (object.getExpiryTime() > -1L)
            object.startFromNow();

        SoftReference<Object> data = new SoftReference<>(object.getValue());

        if (storage.containsKey(object.getKey()))
            storage.get(object.getKey()).put(object.getId(), data);
        else
            storage.put(object.getKey(), new HashMap() {
                {
                    put(object.getId(), data);
                }
            });

        cleaningUpQueue.put(object);
    }

    @Override
    public void remove(String key, String id) {
        if (storage.containsKey(key)) storage.get(key).remove(id);
    }

    @Override
    public void remove(String key) {
        storage.remove(key);
    }

    @Override
    public Optional<SoftReference<Object>> get(String key, String id) {
        return storage.containsKey(key) && storage.get(key).containsKey(id)
                ? Optional.ofNullable(storage.get(key).get(id))
                : Optional.empty();
    }

    @Override
    public Map<Object, SoftReference<Object>> get(String key) {
        return storage.containsKey(key) ? storage.get(key) : new HashMap<>();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void truncate(String key) {
        if (storage.containsKey(key)) storage.get(key).clear();
    }

    @Override
    public long keySize() {
        return storage.size();
    }

    @Override
    public long size() {
        return storage.entrySet().stream().mapToInt(entry -> entry.getValue().size()).sum();
    }

    @Override
    public long size(String key) {
        return storage.containsKey(key) ? storage.get(key).size() : -1;
    }

}