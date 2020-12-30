package com.pineframework.core.cache.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface HashOperations<H, K, V> {
    Long delete(H var1, Object... var2);

    Boolean hasKey(H var1, Object var2);

    V get(H var1, Object var2);

    List<V> multiGet(H var1, Collection<K> var2);

    Long increment(H var1, K var2, long var3);

    Double increment(H var1, K var2, double var3);

    Set<K> keys(H var1);

    Long lengthOfValue(H var1, K var2);

    Long size(H var1);

    void putAll(H var1, Map<? extends K, ? extends V> var2);

    void put(H var1, K var2, V var3);

    Boolean putIfAbsent(H var1, K var2, V var3);

    List<V> values(H var1);

    Map<K, V> entries(H var1);

}
