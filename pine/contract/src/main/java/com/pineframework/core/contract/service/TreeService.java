package com.pineframework.core.contract.service;

public interface TreeService<I, M> {

    M[] findChildren(I id);

    M[] findTreeAsList(I id);

    M[] findSubTreeAsList(I id);

    M findTree(I id);
}
