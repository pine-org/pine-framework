package com.pineframework.core.tutorial.activemqsample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {
        "com.pineframework.core.messaging.activemq",
        "com.pineframework.core.i18n",
        "com.pineframework.core.business",
        "com.pineframework.core.springbootdependencies"})
public class ActiveMqSampleConfiguration {
}
