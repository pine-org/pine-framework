package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.paging.PageMetadataView;
import com.pineframework.core.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Page Model")
class PageModelTest extends AbstractTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    @Test
    @DisplayName("print specific view of model")
    void printSpecificView() throws JsonProcessingException {
        Page page = new Page();
        String jsonString = mapper.writerWithView(PageMetadataView.class).writeValueAsString(page);
        Assertions.assertTrue(jsonString.contains("offset"));
        Assertions.assertTrue(jsonString.contains("size"));
        Assertions.assertFalse(jsonString.contains("index"));
        Assertions.assertFalse(jsonString.contains("indices"));
        Assertions.assertFalse(jsonString.contains("count"));
        Assertions.assertFalse(jsonString.contains("content"));
        Assertions.assertFalse(jsonString.contains("filters"));
        logInfo(jsonString);
    }
}