package com.pineframework.core.spring.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static java.util.Arrays.asList;

public final class TestRestUtils {

    public static final HttpHeaders JSON_HTTP_HEADERS = createJsonHttpHeaders();

    private TestRestUtils() {
    }

    public static HttpHeaders createJsonHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static <T> HttpEntity<String> createJsonBody(T model) {
        return new HttpEntity<>(model.toString(), JSON_HTTP_HEADERS);
    }
}
