package com.pineframework.core.spring.restapi.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineframework.core.datamodel.paging.Page;
import io.vavr.control.Try;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PageConverter implements Converter<String, Page> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Page convert(String source) {
        return Try.of(() -> objectMapper.readValue(source, Page.class)).get();
    }
}
