package com.pineframework.core.spring.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties")
@PropertySource("classpath:messages_fa.properties")
public class I18nConfig {

}
