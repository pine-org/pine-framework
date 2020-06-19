package com.pineframework.core.spring.test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public abstract class AbstractRestfulWebService<T> {

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    @Value("${server.address}")
    protected String host;

    @Value("${server.servlet.context-path}")
    protected String context;

    protected HttpHeaders headers;

    protected Map<String, T> data = new HashMap();

    public AbstractRestfulWebService() {
        this.headers = createJsonHttpHeaders();
        this.initData(this.data);
    }

    public abstract void initData(Map<String, T> models);

    public final T getData(String name) {
        return this.data.get(name);
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
        String url = "http://" + host + ":" + port + "/" + context + getRelativeUri();
        return restTemplate.postForEntity(url, createRequestBody(m), String.class);
    }

//    protected abstract String getHost();

    protected abstract String getRelativeUri();
}
