package com.pineframework.core.rest.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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
        return ResponseEntity.ok("UP");
    }

}
