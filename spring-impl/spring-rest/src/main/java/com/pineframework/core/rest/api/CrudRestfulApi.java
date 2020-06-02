package com.pineframework.core.rest.api;

import com.pineframework.core.contract.restapi.CreateValidationGroup;
import com.pineframework.core.contract.restapi.UpdateValidationGroup;
import com.pineframework.core.contract.service.entityservice.CrudService;
import com.pineframework.core.datamodel.model.FlatTransient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
public interface CrudRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends CrudService<I, M>>
        extends Rest<S> {

    /**
     * expose save service on http
     * never override
     *
     * @param model
     * @param errors
     * @return ID
     */
    @ApiOperation(value = "${restfulApi.save.value}", notes = "${restfulApi.save.notes}")
    @PostMapping
    default ResponseEntity<I> save(
            @ApiParam(name = "Model", value = "${restfulApi.save.param}", required = true)
            @Validated(CreateValidationGroup.class) @RequestBody M model,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeSave(model);
        ResponseEntity<I> response = saveAction(model);
        afterSave(model);
        return response;
    }

    /**
     * execute before save operation
     *
     * @param model
     */
    default void beforeSave(M model) {
    }

    /**
     * execute after save operation
     *
     * @param model
     */
    default void afterSave(M model) {
    }

    /**
     * save operation
     * overridable
     *
     * @param model
     * @return ID
     */
    default ResponseEntity<I> saveAction(M model) {
        return ResponseEntity.of(getService().save(model));
    }

    /**
     * expose find by id service on http
     * never override
     *
     * @param id
     * @return value object
     */
    @ApiOperation(value = "${restfulApi.findById.value}", notes = "${restfulApi.findById.notes}")
    @GetMapping("/{id}")
    default ResponseEntity<M> findById(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findById.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        return ResponseEntity.of(getService().findById(id));
    }

    /**
     * expose update service on http
     * never override
     *
     * @param model
     * @param errors
     */
    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PutMapping
    default void update(
            @ApiParam(name = "Value Object", value = "${restfulApi.update.param}", required = true)
            @Validated(UpdateValidationGroup.class) @RequestBody M model,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeUpdate(model);
        updateAction(model);
    }

    /**
     * execute before update action
     *
     * @param model
     */
    default void beforeUpdate(M model) {
    }

    /**
     * execute before update action
     *
     * @param model
     */
    default void afterUpdate(M model) {
    }

    /**
     * update operation
     * is overridable
     *
     * @param model
     */
    default void updateAction(M model) {
        getService().update(model);
    }

    /**
     * expose delete service on http
     * never override
     *
     * @param id
     */
    @ApiOperation(value = "${restfulApi.delete.value}", notes = "${restfulApi.delete.notes}")
    @DeleteMapping("/{id}")
    default void delete(
            @ApiParam(name = "ID",
                    value = "${restfulApi.delete.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        deleteAction(id);
    }

    /**
     * delete operation
     * is overridable
     *
     * @param id
     */
    default void deleteAction(I id) {
        getService().delete(id);
    }

}
