package com.pineframework.core.sample.activemq;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.pineframework.core.messaging.activemq"})
public class LocalConfiguration {
}
