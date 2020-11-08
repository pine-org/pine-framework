package com.pineframework.core.datamodel.exception;


/**
 * When the Enum does not find, then throw {@code NotFoundEquivalentEnum}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundEquivalentEnum extends AbstractException {

    public static final int CODE = 3;

    public NotFoundEquivalentEnum(Object... args) {
        super("error.business.enum.notFound", CODE, args);
    }

}
