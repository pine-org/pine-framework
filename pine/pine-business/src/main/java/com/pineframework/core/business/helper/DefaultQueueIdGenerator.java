package com.pineframework.core.business.helper;

import com.pineframework.core.contract.service.QueueIdGenerator;

import java.util.UUID;

public class DefaultQueueIdGenerator implements QueueIdGenerator<String> {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }
}
