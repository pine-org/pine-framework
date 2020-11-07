package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.datamodel.exception.CoreException;
import com.pineframework.core.datamodel.exception.ExceptionArray;
import com.pineframework.core.datamodel.exception.NotFoundDataException;
import com.pineframework.core.datamodel.exception.NotFoundEquivalentEnum;
import com.pineframework.core.datamodel.exception.NotSameVersionException;
import com.pineframework.core.helper.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is exception provider that only return the errors as a json response
 * and it is a final class
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@RestControllerAdvice
@Qualifier("pineExceptionProvider")
public final class ExceptionProvider extends AbstractExceptionProvider {

    private final Logger logger = LogUtils.getLogger(ExceptionProvider.class);

    /**
     * when threw the core exception
     *
     * @param e exception
     * @return the error {@code ErrorResponse}
     */
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse[]> coreException(CoreException e) {
        return response(error(e));
    }

    /**
     * when version of input date is not same as the version of persisted date
     *
     * @param e exception
     * @return the error {@code ErrorResponse}
     */
    @ExceptionHandler(NotSameVersionException.class)
    public ResponseEntity<ErrorResponse[]> notSameVersion(NotSameVersionException e) {
        return response(error(e));
    }

    /**
     * when throw NotFoundEquivalentEnum
     *
     * @param e exception
     * @return the error {@code ErrorResponse}
     */
    @ExceptionHandler(NotFoundEquivalentEnum.class)
    public ResponseEntity<ErrorResponse[]> notFoundEquivalentEnum(NotFoundEquivalentEnum e) {
        return response(error(e));
    }

    /**
     * when throw NotFoundEquivalentEnum
     *
     * @param e exception
     * @return the error {@code ErrorResponse}
     */
    @ExceptionHandler(NotFoundDataException.class)
    public ResponseEntity<ErrorResponse[]> notFoundData(NotFoundDataException e) {
        return response(error(e));
    }

    /**
     * when execute validation on value objects
     *
     * @param e exception
     * @return the error {@code ErrorResponse}
     */
    @ExceptionHandler(ExceptionArray.class)
    public ResponseEntity<ErrorResponse[]> exceptionCollection(ExceptionArray e) {
        return response(error(e.getExceptions()));
    }

}
