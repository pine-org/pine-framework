package com.pineframework.core.datamodel.exception;


/**
 * When value object is invalid throw this exception
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class NotFoundEquivalentEnum extends AbstractException {

    public NotFoundEquivalentEnum(Object... args) {
        super("error.orm.convertColumnToEnum", 2, args);
    }

}
