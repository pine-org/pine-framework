package com.pineframework.core.rest.api;

import com.pineframework.core.business.exception.CoreException;
import com.pineframework.core.business.exception.ExceptionArray;
import com.pineframework.core.business.exception.NotFoundEquivalentEnum;
import com.pineframework.core.business.exception.NotSameVersionException;
import com.pineframework.core.helper.LogUtils;
import com.pineframework.core.rest.ErrorResponse;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is exception provider that only return json response
 * and is final class
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@RestControllerAdvice
public final class ExceptionProvider extends AbstractExceptionProvider {

    private final Logger logger = LogUtils.getLogger(ExceptionProvider.class);

    /**
     * when execute threw core e
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> coreException(CoreException e) {
        return response(error(e));
    }

    /**
     * when version of input date is not same as version of persisted date
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotSameVersionException.class)
    public ResponseEntity<ErrorResponse> notSameVersion(NotSameVersionException e) {
        return response(error(e));
    }

    /**
     * When throw NotFoundEquivalentEnum
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundEquivalentEnum.class)
    public ResponseEntity<ErrorResponse> notFoundEquivalentEnum(NotFoundEquivalentEnum e) {
        return response(error(e));
    }

    /**
     * when execute validation on value objects
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ExceptionArray.class)
    public ResponseEntity<ErrorResponse[]> exceptionCollection(ExceptionArray e) {
        return response(error(e.getExceptions()));
    }

    /**
     * when throw SQL grammar e
     *
     * @param e
     * @return
     */
    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<ErrorResponse> jdbcException(JDBCException e) {
        return response(error(new CoreException(e.getMessage(), e.getErrorCode())));
    }

}
