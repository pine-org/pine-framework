package com.pineframework.core.datastructure.model.optional;

import static java.util.Objects.isNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class BooleanOptional {

    private final Boolean value;

    private boolean theOther;

    private BooleanOptional(Boolean value) {
        this.value = isNull(value) ? Boolean.FALSE : value;
    }

    public static BooleanOptional check(Boolean value) {
        return new BooleanOptional(value);
    }

    public BooleanOptional ifTrue(Runnable runnable) {
        if (value) {
            runnable.run();

        } else {
            theOther = true;
        }

        return this;
    }

    public void other(Runnable runnable) {
        if (theOther) {
            runnable.run();
        }
    }

    public BooleanOptional ifFalse(Runnable runnable) {
        if (!value) {
            runnable.run();

        } else {
            theOther = true;
        }

        return this;
    }

    public Boolean get() {
        return value;
    }

}
