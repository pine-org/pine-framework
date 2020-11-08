package com.pineframework.core.datamodel.exception;


/**
 * When the data is required, then throw {@code RequiredException}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class RequiredException extends AbstractException {

    public static final int CODE = 5;

    public RequiredException(Object... args) {
        super("error.validation.notNull", CODE, args);
    }

}
