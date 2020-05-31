package com.pineframework.core.contract.config;

import javax.jms.Queue;

public interface QueueConfiguration {

    Queue mainQueue();

    Queue statusQueue();
}
