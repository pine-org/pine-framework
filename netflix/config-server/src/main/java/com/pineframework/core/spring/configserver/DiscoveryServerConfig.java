package com.pineframework.core.spring.configserver;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Profile(value = "eureka-server")
@Configuration
@EnableDiscoveryClient
public class DiscoveryServerConfig {
}
