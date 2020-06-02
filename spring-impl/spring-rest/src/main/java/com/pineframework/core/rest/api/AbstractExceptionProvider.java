package com.pineframework.core.rest.api;

import com.pineframework.core.business.exception.AbstractException;
import com.pineframework.core.rest.ErrorResponse;
import com.pineframework.core.rest.I18nHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

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
     * Create error response from AbstractException.
     *
     * @param exception
     * @return error
     */
    public ErrorResponse error(AbstractException exception) {
        return ErrorResponse.from(i18nHelper.toLocale(exception));
    }

    /**
     * Create error response list from children of AbstractExceptions
     *
     * @param exceptions
     * @return list of error
     */
    public ErrorResponse[] error(AbstractException[] exceptions) {
        return Arrays.stream(exceptions)
                .peek(i18nHelper::toLocale)
                .map(ErrorResponse::from)
                .toArray(ErrorResponse[]::new);
    }

    public ResponseEntity<ErrorResponse> response(ErrorResponse error) {
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    public ResponseEntity<ErrorResponse[]> response(ErrorResponse[] errors) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }
}
