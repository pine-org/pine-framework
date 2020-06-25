package com.pineframework.core.datamodel.exception;

/**
 * All of exceptions must extend AbstractException.
 * All of exceptions are runtime exception and will handle with centralized exception handling mechanism.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class ExceptionArray extends RuntimeException {

    private final AbstractException[] exceptions;

    private ExceptionArray(AbstractException[] exceptions) {
        this.exceptions = exceptions;
    }

    public static ExceptionArray of(AbstractException[] exceptions) {
        return new ExceptionArray(exceptions);
    }

    public AbstractException[] getExceptions() {
        return exceptions;
    }
}
