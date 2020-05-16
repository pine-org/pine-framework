package com.pineframework.core.datastructure.model.paging;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pineframework.core.helper.serializing.ArrayDeserializer;

import java.util.List;

public class SearchFilter {

    private Operator operator;

    private String fieldName;

    @JsonDeserialize(using = ArrayDeserializer.class)
    private List<Object> values;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
