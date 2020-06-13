package com.pineframework.core.contract.service;

import java.util.Optional;

public interface CrudService<I, M> {
    Optional<I> save(M m);

    Optional<M> findById(I id);

    void update(M m);

    void delete(I id);
}
