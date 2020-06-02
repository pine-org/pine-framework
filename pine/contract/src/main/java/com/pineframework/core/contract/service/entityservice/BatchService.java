package com.pineframework.core.contract.service.entityservice;

import java.util.function.Consumer;

public interface BatchService<I, M> {
    I[] batchSave(M[] models);

    void batchUpdate(M[] models);

    void batchDelete(I[] identities);

    I[] batchOperations(M[] models, I[] identities);

    void batch(M[] models, Consumer<M> operation);
}
