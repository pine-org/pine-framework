package com.pineframework.core.cache.redis;

import com.pineframework.core.cache.event.RowOperation;
import com.pineframework.core.cache.event.SimpleCacheObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class RedisOperationContext {
    public static final Map<RowOperation, Function<SimpleCacheObject, Consumer<HashOperations<String, Object, Object>>>>
            HASH_OPERATION = new HashMap<>();

    static {
        HASH_OPERATION.put(RowOperation.INSERT, obj -> ops -> ops.put(obj.getKey(),
                String.valueOf(obj.getId()), obj.getValue()));
        HASH_OPERATION.put(RowOperation.UPDATE, obj -> ops -> ops.put(obj.getKey(),
                String.valueOf(obj.getId()), obj.getValue()));
        HASH_OPERATION.put(RowOperation.DELETE, obj -> ops -> ops.delete(obj.getKey(),
                String.valueOf(obj.getId())));
    }

    public static Consumer<HashOperations<String, Object, Object>> getHashOperation(SimpleCacheObject object) {
        return HASH_OPERATION.get(object.getOperation()).apply(object);
    }

}