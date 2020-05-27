package com.pineframework.core.business.helper;

import com.pineframework.core.contract.service.QueueIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultQueueIdGenerator implements QueueIdGenerator<String> {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }
}
