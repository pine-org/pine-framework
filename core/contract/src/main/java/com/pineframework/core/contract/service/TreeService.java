package com.pineframework.core.contract.service;

import java.util.Optional;

public interface TreeService<I, M> {

    M[] findChildren(I id);

    M[] findTreeAsList(I id);

    M[] findSubTreeAsList(I id);

    Optional<M> findTree(I id);
}
