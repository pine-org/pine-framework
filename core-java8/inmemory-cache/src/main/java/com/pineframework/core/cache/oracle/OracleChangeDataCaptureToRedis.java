package com.pineframework.core.cache.oracle;

import com.pineframework.core.cache.event.SimpleCacheObject;
import com.pineframework.core.cache.redis.HashOperations;
import com.pineframework.core.cache.redis.RedisOperationContext;
import com.pineframework.core.contract.enumeration.Convertible;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class OracleChangeDataCaptureToRedis extends OracleChangeDataCapture {

    private HashOperations<String, Object, Object> redisHashTemplate;

    @Override
    protected void process(SimpleCacheObject object) {
        RedisOperationContext.getHashOperation(object).accept(redisHashTemplate);

    }

    @Override
    protected <T extends Enum> Object getValueOfEnum(T item) {
        return ((Convertible) item).getValue();
    }
}