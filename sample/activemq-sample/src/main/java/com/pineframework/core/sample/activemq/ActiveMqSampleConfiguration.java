package com.pineframework.core.sample.activemq;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(value = {
        "com.pineframework.core.messaging.activemq",
        "com.pineframework.core.i18n",
        "com.pineframework.core.springbootdependencies"})
public class ActiveMqSampleConfiguration {
}
