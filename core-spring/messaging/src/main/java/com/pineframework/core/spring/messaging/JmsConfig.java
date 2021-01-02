package com.pineframework.core.spring.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
@Profile(value = "messaging")
public class JmsConfig {
}
