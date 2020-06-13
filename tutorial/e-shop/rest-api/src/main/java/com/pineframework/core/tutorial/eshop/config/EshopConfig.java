package com.pineframework.core.tutorial.eshop.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.pineframework.core.tutorial.eshop.business.domain")
@ComponentScan(value = {
        "com.pineframework.core.spring.restapi",
        "com.pineframework.core.spring.transaction",
        "com.pineframework.core.spring.messaging",
        "com.pineframework.core.spring.activemq",
})
public class EshopConfig {
}
