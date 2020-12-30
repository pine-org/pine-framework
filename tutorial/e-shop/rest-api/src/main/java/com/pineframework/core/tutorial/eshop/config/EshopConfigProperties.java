package com.pineframework.core.tutorial.eshop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"properties-file", "test"})
@ComponentScan(value = {"com.pineframework.core.spring.properties"})
public class EshopConfigProperties {
}
