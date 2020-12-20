package com.pineframework.core.cache.javacache;

import com.pineframework.core.cache.event.DelayedCacheObject;
import com.pineframework.core.cache.event.RowOperation;
import com.pineframework.core.cache.template.HashTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public class JvmHashOperationContext {
    public static final Map<RowOperation, Function<DelayedCacheObject, Consumer<HashTemplate<DelayedCacheObject>>>>
            MAP_OPERATION = new HashMap<>();

    static {
        MAP_OPERATION.put(RowOperation.INSERT, obj -> ops -> ops.save(obj));
        MAP_OPERATION.put(RowOperation.UPDATE, obj -> ops -> ops.save(obj));
        MAP_OPERATION.put(RowOperation.DELETE, obj -> ops -> ops.remove(obj.getKey(), obj.getKey()));
    }

    public static Consumer<HashTemplate<DelayedCacheObject>> getMapOperation(DelayedCacheObject object) {
        return MAP_OPERATION.get(object.getOperation()).apply(object);
    }

}
