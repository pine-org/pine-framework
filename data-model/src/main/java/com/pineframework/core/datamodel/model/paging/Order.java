package com.pineframework.core.datamodel.model.paging;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {

    private String fieldName;

    private OrderType type;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return fieldName.equals(order.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName);
    }
}
