package com.pineframework.core.cache.oracle;

import com.pineframework.core.cache.event.DelayedCacheObject;
import com.pineframework.core.cache.event.SimpleCacheObject;
import com.pineframework.core.cache.javacache.JvmHashOperationContext;
import com.pineframework.core.cache.template.HashTemplate;
import com.pineframework.core.contract.enumeration.Convertible;

import java.lang.ref.SoftReference;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class OracleChangeDataCaptureToJvmCache extends OracleChangeDataCapture {

    private HashTemplate<DelayedCacheObject> jvmHashTemplate;

    @Override
    protected void process(SimpleCacheObject object) {
        DelayedCacheObject delayedCacheObject = new DelayedCacheObject(object.getKey(), String.valueOf(object.getId()),
                new SoftReference<>(object.getValue()), object.getOperation(), -1L);
        JvmHashOperationContext.getMapOperation(delayedCacheObject).accept(jvmHashTemplate);
    }

    @Override
    protected <T extends Enum> Object getValueOfEnum(T item) {
        return ((Convertible) item).getValue();
    }
}