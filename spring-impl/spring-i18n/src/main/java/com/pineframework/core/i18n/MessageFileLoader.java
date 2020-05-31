package com.pineframework.core.i18n;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties")
@PropertySource("classpath:messages_fa.properties")
public class MessageFileLoader {

}
