package com.pineframework.core.spring.restapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:info.properties")
@ConfigurationProperties(prefix = "info")
public class SwaggerConfig {

    @Value("${configuration.scan-package}")
    private String scanPackage;

    @Value("${info.title}")
    private String title;

    @Value("${info.description}")
    private String description;

    @Value("${info.license}")
    private String license;

    @Value("${info.license.url}")
    private String licenseUrl;

    @Value("${info.version}")
    private String version;

    @Value("${info.contact.name}")
    private String name;

    @Value("${info.contact.url}")
    private String webSite;

    @Value("${info.contact.email}")
    private String email;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(title)
                        .description(description)
                        .contact(new Contact().name(name).email(email).url(webSite))
                        .license(new License().name(license).url(licenseUrl))
                        .version(version));
    }

}