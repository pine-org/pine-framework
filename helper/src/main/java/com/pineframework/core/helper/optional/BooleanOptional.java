package com.pineframework.core.helper.optional;

import static java.util.Objects.isNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class BooleanOptional {

    private final Boolean value;

    private Runnable theOther = () -> System.out.println("else statement not defined!");

    private BooleanOptional(Boolean value) {
        this.value = isNull(value) ? Boolean.FALSE : value;
    }

    public static BooleanOptional of(Boolean value) {
        return new BooleanOptional(value);
    }

    public void ifTrue(Runnable runnable) {
        if (value) runnable.run();
        else theOther.run();
    }

    public BooleanOptional other(Runnable runnable) {
        this.theOther = runnable;
        return this;
    }

    public void ifFalse(Runnable runnable) {
        if (!value) runnable.run();
        else theOther.run();
    }

    public Boolean get() {
        return value;
    }
}
