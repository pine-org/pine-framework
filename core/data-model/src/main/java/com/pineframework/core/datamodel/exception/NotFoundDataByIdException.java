package com.pineframework.core.datamodel.exception;


/**
 * When the model does not find, then throw {@code NotFoundDataException}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundDataByIdException extends AbstractException {

    public static final int CODE = 1;

    public NotFoundDataByIdException(Object... args) {
        super("error.business.data-id.notFound", CODE, args);
    }

}
