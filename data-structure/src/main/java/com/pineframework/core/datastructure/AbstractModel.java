package com.pineframework.core.datastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.Serializable;

public abstract class AbstractModel<ID extends Serializable> implements Serializable {

    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Try.of(() -> new ObjectMapper().writeValueAsString(this)).get();
    }

}
