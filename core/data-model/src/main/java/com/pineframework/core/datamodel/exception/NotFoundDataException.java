package com.pineframework.core.datamodel.exception;


/**
 * When the model does not find, then throw {@code NotFoundDataException}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundDataException extends AbstractException {

    public NotFoundDataException(Object... args) {
        super("error.business.data.notFound", 1, args);
    }

}
