package com.pineframework.core.business.helper;

import com.pineframework.core.contract.service.CorrelationIdGenerator;

import java.util.UUID;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class DefaultQueueIdGenerator implements CorrelationIdGenerator {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }
}
