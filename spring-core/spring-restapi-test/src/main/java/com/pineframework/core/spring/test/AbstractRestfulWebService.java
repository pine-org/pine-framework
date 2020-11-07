package com.pineframework.core.spring.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineframework.core.helper.GenericUtils;
import com.pineframework.core.test.AbstractTest;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;

import static com.pineframework.core.spring.test.TestRestUtils.createJsonBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class AbstractRestfulWebService<I, T> extends AbstractTest<T> {

    protected final Class<T> modelType;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Value("${server.address}")
    protected String host;

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String app;

    protected ObjectMapper objectMapper = new ObjectMapper();

    public AbstractRestfulWebService(Map<String, T> storage) {
        super(storage);
        modelType = (Class<T>) GenericUtils.extract(getClass(), 1);
    }

    protected URI post(T m, String relativeUri) {
        ResponseEntity<String> response = restTemplate.postForEntity(makeUri(relativeUri), createJsonBody(m), String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertNull(response.getBody());
        return response.getHeaders().getLocation();
    }

    protected T getById(I id, String relativeUri) {
        ResponseEntity<String> response = restTemplate.getForEntity(makeUri(relativeUri) + "/{id}", String.class, id);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return Try.of(() -> objectMapper.readValue(response.getBody(), modelType)).get();
    }

    protected String makeUri(String relativeUri) {
        return "http://" + host + ":" + port + app + relativeUri;
    }
}
