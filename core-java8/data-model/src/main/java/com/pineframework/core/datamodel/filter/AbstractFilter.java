package com.pineframework.core.datamodel.filter;

public abstract class AbstractFilter<E> implements Filter<E> {

    protected String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
