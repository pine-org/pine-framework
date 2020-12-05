package com.pineframework.core.helper.optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class BooleanOptional {

    private final Boolean value;

    private Runnable theOther = null;

    private BooleanOptional(Boolean value) {
        this.value = isNull(value) ? Boolean.FALSE : value;
    }

    public static BooleanOptional of(Boolean value) {
        return new BooleanOptional(value);
    }

    public void ifTrue(Runnable runnable) {
        if (value) runnable.run();
        else if (nonNull(theOther)) theOther.run();
    }

    public BooleanOptional other(Runnable runnable) {
        this.theOther = runnable;
        return this;
    }

    public void ifFalse(Runnable runnable) {
        if (!value) runnable.run();
        else if (nonNull(theOther)) theOther.run();
    }

    public Boolean get() {
        return value;
    }
}
