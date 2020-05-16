package com.pineframework.core.datastructure.model.paging;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pineframework.core.helper.serializing.ArrayDeserializer;

import java.io.Serializable;

public class Filter implements Serializable {

    private String fieldName;

    @JsonDeserialize(using = ArrayDeserializer.class)
    private Object[] values;

    private Operator operator;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
