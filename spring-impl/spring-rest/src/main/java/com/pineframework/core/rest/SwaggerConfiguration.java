package com.pineframework.core.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:info.properties")
@ConfigurationProperties(prefix = "info")
@ComponentScan(value = {"com.pineframework.core.rest"})
public class SwaggerConfiguration {

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
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(scanPackage))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(title)
                .description(description)
                .contact(new Contact(name, webSite, email))
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }
}