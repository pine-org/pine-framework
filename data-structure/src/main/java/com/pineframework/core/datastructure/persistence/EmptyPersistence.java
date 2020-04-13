package com.pineframework.core.datastructure.persistence;

public class EmptyPersistence implements Persistable<Integer> {

    @Override
    public Integer getId() {
        return Integer.valueOf(1);
    }

    @Override
    public Integer getVersion() {
        return Integer.valueOf(-1);
    }
}
