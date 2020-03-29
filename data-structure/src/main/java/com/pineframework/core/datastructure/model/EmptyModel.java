package com.pineframework.core.datastructure.model;

public class EmptyModel implements Transient<Integer, Integer> {

    private static final long serialVersionUID = 7595836524580695687L;

    @Override
    public Integer getId() {
        return Integer.valueOf(1);
    }

    @Override
    public Integer getVersion() {
        return Integer.valueOf(1);
    }
}
