package com.pineframework.core.datamodel.exception;


/**
 * When the Enum does not find, then throw {@code NotFoundEquivalentEnum}.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundEquivalentEnum extends AbstractException {

    public NotFoundEquivalentEnum(Object... args) {
        super("error.business.enum.notFind", 2, args);
    }

}
