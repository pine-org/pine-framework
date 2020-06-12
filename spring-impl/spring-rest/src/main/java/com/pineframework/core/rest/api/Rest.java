package com.pineframework.core.rest.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.ResponseEntity.ok;

/**
 * all Restful web service must implements this interface
 *
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Rest<S> {
    S getService();

    @ApiOperation(value = "${restfulApi.running.value}", notes = "${restfulApi.running.notes}")
    @GetMapping("status")
    default ResponseEntity<String> getStatus() {
        return ok("UP");
    }

    @ApiOperation(value = "${restfulApi.running.value}", notes = "${restfulApi.running.notes}")
    @GetMapping("options")
    default ResponseEntity<String> getOptions() {
        return ok().allow(POST, GET, PATCH, DELETE, PUT, OPTIONS).build();
    }

}
