package com.pineframework.core.datamodel.sort;

public abstract class AbstractSort<E> implements Sort<E> {

    protected String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
