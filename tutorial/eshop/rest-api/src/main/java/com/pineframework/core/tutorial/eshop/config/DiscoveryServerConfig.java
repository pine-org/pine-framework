package com.pineframework.core.tutorial.eshop.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = "eureka-server")
@Configuration
@EnableDiscoveryClient
public class DiscoveryServerConfig {
}
