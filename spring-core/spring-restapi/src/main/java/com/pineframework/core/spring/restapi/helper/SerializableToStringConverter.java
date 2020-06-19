package com.pineframework.core.spring.restapi.helper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SerializableToStringConverter implements Converter<Serializable, String> {

    @Override
    public String convert(Serializable source) {
        return String.valueOf(source);
    }
}
