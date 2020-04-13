package com.pineframework.core.configurationloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationLoaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationLoaderApplication.class, args);
    }

}

