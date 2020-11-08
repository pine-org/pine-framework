package com.pineframework.core.datamodel.exception;


/**
 * When the current persisted model version and current updated data version are not the same, then
 * throw {@code NotSameVersionException}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotSameVersionException extends AbstractException {

    public static final int CODE = 4;

    public NotSameVersionException(Object... args) {
        super("error.business.data.version", CODE, args);
    }

}
