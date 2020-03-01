package com.pineframework.core.datastructure;

import static java.util.Objects.isNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class BooleanOptional {

    private final Boolean value;

    private BooleanOptional(Boolean value) {
        this.value = isNull(value) ? Boolean.FALSE : value;
    }

    public static BooleanOptional of(Boolean value) {
        return new BooleanOptional(value);
    }

    public BooleanOptional ifTrue(Runnable runnable) {
        if (value) {
            runnable.run();
        }

        return this;
    }

    public BooleanOptional ifFalse(Runnable runnable) {
        if (!value) {
            runnable.run();
        }

        return this;
    }

    public Boolean get() {
        return value;
    }

}
