package com.pineframework.core.contract.service;

import java.util.function.Consumer;

/**
 * Business logic layer to support batch operation on flat data structure.
 *
 * @param <I> identity
 * @param <M> transient type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchService<I, M> {

    /**
     * Save atleast one data model and return their identities. Their are two methods to execute around the iteration,
     * it means {@code beforeBatchSave} and {@code afterBatchSave} also there are two another methods to execute around
     * the save operation per model and they are {@code beforeSave} and {@code afterSave}.
     *
     * @param models transient objects
     * @return identity array
     */
    I[] batchSave(M[] models);

    /**
     * Update atleast one data model. Their are two methods to execute around the iteration, it means
     * {@code beforeBatchUpdate} and {@code afterBatchUpdate} also there are two another methods to execute around the
     * update operation per model and they are {@code beforeUpdate} and {@code afterUpdate}.
     *
     * @param models transient objects
     */
    void batchUpdate(M[] models);

    /**
     * Delete atleast one data model. Their are two methods to execute around the iteration, it means
     * {@code beforeBatchDelete} and {@code afterBatchDelete} also there are two another methods to execute around the
     * delete operation per model and they are {@code beforeDelete} and {@code afterDelete}.
     *
     * @param identities identities
     */
    void batchDelete(I[] identities);

    /**
     * This method execute save, update and delete operation in the one operation, so that after distinguish new models,
     * and the models  that are supposed to update or delete, the operations are performed on the models.
     *
     * @param models     transient objects include new objects and new updates
     * @param identities identities of objects fetched already
     * @return identity array of new objects
     */
    I[] batchOperations(M[] models, I[] identities);

    /**
     * Execute specific operation on bunch of models.
     *
     * @param models    transient objects
     * @param operation the operation
     */
    void batch(M[] models, Consumer<M> operation);
}
