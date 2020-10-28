package com.pineframework.core.datamodel.exception;


/**
 * When the data is required, then throw {@code RequiredException}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class RequiredException extends AbstractException {

    public RequiredException(String... args) {
        super("error.validation.notNull", 3, args);
    }

}
