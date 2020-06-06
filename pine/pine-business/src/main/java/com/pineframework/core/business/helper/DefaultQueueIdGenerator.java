package com.pineframework.core.business.helper;

import com.pineframework.core.contract.service.CorrelationIdGenerator;

import java.util.UUID;

public class DefaultQueueIdGenerator implements CorrelationIdGenerator {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }
}
