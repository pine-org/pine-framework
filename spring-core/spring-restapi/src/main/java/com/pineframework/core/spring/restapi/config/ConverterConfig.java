package com.pineframework.core.spring.restapi.config;

import com.pineframework.core.spring.restapi.helper.PageConverter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConverterConfig extends SpringBootServletInitializer {

    @Bean(name = "conversionService")
    public ConversionServiceFactoryBean getConversionService() {

        Set<Converter> converters = new HashSet<>();
        converters.add(new PageConverter());

        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converters);
        return bean;
    }
}
