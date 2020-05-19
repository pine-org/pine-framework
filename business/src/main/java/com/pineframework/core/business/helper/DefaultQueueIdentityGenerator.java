package com.pineframework.core.business.helper;

import com.pineframework.core.contract.service.QueueIdentityGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultQueueIdentityGenerator implements QueueIdentityGenerator<String> {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }
}
