package com.pineframework.core.spring.restapi.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.ResponseEntity.ok;

/**
 * expose system status and options restful web services.
 * the endpoints never should be overridden.
 * all Restful web service must implements this interface.
 *
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@CrossOrigin
public interface Rest<S> {
    S getService();

    /**
     * get the system status
     *
     * @return string that show the system is up
     */
    @Operation(summary = "${restfulApi.health.up.summary}",
            description = "${restfulApi.health.up.description}",
            method = "GET")
    @ApiResponse(responseCode = "200", description = "${restfulApi.health.up.response.200}")
    @GetMapping("status")
    default ResponseEntity<String> getStatus() {
        return ok("UP");
    }

    /**
     * get the http verbs that they are used in system
     *
     * @return http verbs
     */
    @Operation(summary = "${restfulApi.health.options.summary}",
            description = "${restfulApi.health.options.description}",
            method = "GET")
    @ApiResponse(responseCode = "200", description = "${restfulApi.health.options.response.200}")
    @GetMapping("options")
    default ResponseEntity<String> getOptions() {
        return ok().allow(POST, GET, PATCH, DELETE, PUT, OPTIONS).build();
    }

}
