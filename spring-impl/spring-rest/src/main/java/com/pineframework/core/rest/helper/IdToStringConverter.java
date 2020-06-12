package com.pineframework.core.rest.helper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class IdToStringConverter implements Converter<Serializable, String> {

    @Override
    public String convert(Serializable source) {
        return source.toString();
    }
}