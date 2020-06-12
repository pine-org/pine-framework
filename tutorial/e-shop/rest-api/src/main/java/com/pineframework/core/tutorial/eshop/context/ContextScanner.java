package com.pineframework.core.tutorial.eshop.context;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.pineframework.core.tutorial.eshop.business.domain")
@ComponentScan(value = {
        "com.pineframework.core.rest",
        "com.pineframework.core.transaction",
        "com.pineframework.core.jms",
        "com.pineframework.core.messaging.activemq",
})
public class ContextScanner {
}
