package com.pineframework.core.spring.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerEurekaApplication.class, args);
    }

}
