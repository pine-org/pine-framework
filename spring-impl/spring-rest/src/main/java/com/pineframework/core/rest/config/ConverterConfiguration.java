package com.pineframework.core.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import static java.util.Arrays.asList;

@Configuration
public class ConverterConfiguration {

    @Bean
    public LinkDiscoverers discoverers() {
        return new LinkDiscoverers(SimplePluginRegistry.create(asList(new CollectionJsonLinkDiscoverer())));
    }

}
