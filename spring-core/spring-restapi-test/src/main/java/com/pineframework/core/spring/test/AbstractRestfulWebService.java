package com.pineframework.core.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

public abstract class AbstractRestfulWebService<T> {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Value("${server.address}")
    protected String host;

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String app;

    protected HttpHeaders headers;

    protected Map<String, T> data = new HashMap();

    public AbstractRestfulWebService() {
        this.headers = createJsonHttpHeaders();
        this.initData(this.data);
    }

    protected HttpHeaders createJsonHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    protected HttpEntity<String> createRequestBody(T model) {
        return new HttpEntity<>(model.toString(), headers);
    }

    protected ResponseEntity<String> post(T m) {
        return restTemplate.postForEntity(makeUri(), createRequestBody(m), String.class);
    }

    protected final String makeUri() {
        return "http://" + host + ":" + port + app + getRelativeUri();
    }

    public final T getData(String name) {
        return this.data.get(name);
    }

    public abstract void initData(Map<String, T> models);

    protected abstract String getRelativeUri();
}
