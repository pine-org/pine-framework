package com.pineframework.core.datamodel.exception;

/**
 * All of exceptions must extend AbstractException.
 * All of exceptions are runtime exception and will handle with centralized exception handling mechanism.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractException extends RuntimeException {

    private final int code;

    private final transient Object[] args;

    public AbstractException(String message, int code, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
        init();
    }

    public void init() {
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

}
