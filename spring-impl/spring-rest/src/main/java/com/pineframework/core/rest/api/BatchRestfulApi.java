package com.pineframework.core.rest.api;

import com.pineframework.core.contract.restapi.CreateValidationGroup;
import com.pineframework.core.contract.restapi.UpdateValidationGroup;
import com.pineframework.core.contract.service.entityservice.BatchService;
import com.pineframework.core.datamodel.model.FlatTransient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

import static com.pineframework.core.rest.ExceptionUtils.checkErrors;


/**
 * exposed CRUD services
 *
 * @param <I> id
 * @param <M> value object
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends BatchService<I, M>>
        extends Rest<S> {

    /**
     * expose batch save, update and delete service on http
     * never override
     *
     * @param models
     * @param errors
     * @return ID
     */
    @ApiOperation(value = "${restfulApi.batchOperations.value}", notes = "${restfulApi.batchOperations.notes}")
    @PostMapping("/batch/operations")
    default ResponseEntity<I[]> batchOperations(
            @ApiParam(name = "Models", value = "${restfulApi.batchOperations.param}", required = true)
            @Validated({CreateValidationGroup.class, UpdateValidationGroup.class}) @RequestBody M[] models,
            @Valid @RequestBody I[] identities,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeBatchOperations(models);
        ResponseEntity<I[]> response = batchOperationsAction(models, identities);
        afterBatchOperations(models);
        return response;
    }

    /**
     * execute before save operation
     *
     * @param model
     */
    default void beforeBatchOperations(M[] model) {
    }

    /**
     * execute before save operation
     *
     * @param model
     */
    default void afterBatchOperations(M[] model) {
    }

    /**
     * batch operation
     * overridable
     *
     * @param model
     * @param identities
     * @return ID
     */
    default ResponseEntity<I[]> batchOperationsAction(M[] model, I[] identities) {
        return ResponseEntity.ok(getService().batchOperations(model, identities));
    }

    /**
     * expose batch save service on http
     * never override
     *
     * @param models
     * @param errors
     * @return ID
     */
    @ApiOperation(value = "${restfulApi.batchSave.value}", notes = "${restfulApi.batchSave.notes}")
    @PostMapping("/batch/save")
    default ResponseEntity<I[]> batchSave(
            @ApiParam(name = "Model", value = "${restfulApi.batchSave.param}", required = true)
            @Validated(CreateValidationGroup.class) @RequestBody M[] models,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeBatchSave(models);
        ResponseEntity<I[]> response = batchSaveAction(models);
        afterBatchSave(models);
        return response;
    }

    /**
     * execute before batch save operation
     *
     * @param model
     */
    default void beforeBatchSave(M[] model) {
    }

    /**
     * execute before batch save operation
     *
     * @param model
     */
    default void afterBatchSave(M[] model) {
    }

    /**
     * batch save operation
     * overridable
     *
     * @param model
     * @return ID
     */
    default ResponseEntity<I[]> batchSaveAction(M[] model) {
        return ResponseEntity.ok(getService().batchSave(model));
    }

    /**
     * expose batch update service on http
     * never override
     *
     * @param models
     * @param errors
     * @return ID
     */
    @ApiOperation(value = "${restfulApi.batchUpdate.value}", notes = "${restfulApi.batchUpdate.notes}")
    @PutMapping("/batch/update")
    default void batchUpdate(
            @ApiParam(name = "Model", value = "${restfulApi.batchUpdate.param}", required = true)
            @Validated(UpdateValidationGroup.class) @RequestBody M[] models,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeBatchUpdate(models);
        batchUpdateAction(models);
        afterBatchUpdate(models);
    }

    /**
     * execute before batch update operation
     *
     * @param model
     */
    default void beforeBatchUpdate(M[] model) {
    }

    /**
     * execute before batch update operation
     *
     * @param model
     */
    default void afterBatchUpdate(M[] model) {
    }

    /**
     * batch update operation
     * overridable
     *
     * @param model
     * @return ID
     */
    default void batchUpdateAction(M[] model) {
        getService().batchUpdate(model);
    }

    /**
     * expose batch delete service on http
     * never override
     *
     * @param identities
     */
    @ApiOperation(value = "${restfulApi.batchDelete.value}", notes = "${restfulApi.batchDelete.notes}")
    @PutMapping("/batch/delete")
    default void batchDelete(
            @ApiParam(name = "identities", value = "${restfulApi.batchDelete.param}", required = true)
            @RequestBody I[] identities) {

        batchDeleteAction(identities);
    }

    /**
     * batch delete operation
     * is overridable
     *
     * @param identities
     */
    default void batchDeleteAction(I[] identities) {
        getService().batchDelete(identities);
    }

}
