package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.datamodel.exception.AbstractException;
import com.pineframework.core.spring.restapi.helper.I18nHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * All of centralize exception providers should use this abstract class
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractExceptionProvider {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    protected I18nHelper i18nHelper;

    /**
     * convert {@code AbstractExceptions} derived classes to {@code ErrorResponse} in order to present to the client
     *
     * @param exceptions exception(s)
     * @return error array
     */
    public ErrorResponse[] error(AbstractException... exceptions) {
        return Arrays.stream(exceptions)
                .map(i18nHelper::toLocale)
                .map(ErrorResponse::from)
                .toArray(ErrorResponse[]::new);
    }

    /**
     * create a JSON response from the errors to expose over the HTTP
     *
     * @param errors error(s)
     * @return unit of the errors
     */
    public ResponseEntity<ErrorResponse[]> response(ErrorResponse... errors) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errors);
    }
}
