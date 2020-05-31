package com.pineframework.core.datamodel.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LikeFilter.class, name = "like"),
        @JsonSubTypes.Type(value = EqualsFilter.class, name = "equals")
})
public abstract class AbstractFilter implements Filter {

    protected String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
