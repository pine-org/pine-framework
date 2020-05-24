package com.pineframework.core.tutorial.eshop;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.pineframework.core.tutorial.eshop.business.domain")
@ComponentScan(value = {
        "com.pineframework.core.messaging.activemq",
        "com.pineframework.core.i18n",
        "com.pineframework.core.business",
        "com.pineframework.core.springbootdependencies"})
public class EshopConfiguration {
}
