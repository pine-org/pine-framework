package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.restapi.CreateValidationGroup;
import com.pineframework.core.contract.restapi.UpdateValidationGroup;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.spring.restapi.helper.ErrorUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;


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
    @ResponseStatus(value = CREATED, code = CREATED)
    default ResponseEntity<I> save(
            @ApiParam(name = "Model", value = "${restfulApi.save.param}", required = true)
            @Validated(CreateValidationGroup.class) @RequestBody M model,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        ErrorUtils.checkErrors(errors);

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
        return ResponseEntity.status(CREATED).body(getService().save(model).get());
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
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<M> findById(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findById.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        return ok(getService().findById(id).get());
    }

    /**
     * expose update service on http
     * never override
     *
     * @param model
     * @param errors
     */
    @ApiOperation(value = "${restfulApi.update.value}", notes = "${restfulApi.update.notes}")
    @PatchMapping
    @ResponseStatus(value = ACCEPTED, code = ACCEPTED)
    default void update(
            @ApiParam(name = "Value Object", value = "${restfulApi.update.param}", required = true)
            @Validated(UpdateValidationGroup.class) @RequestBody M model,
            @ApiParam(name = "errors", hidden = true) Errors errors) {

        ErrorUtils.checkErrors(errors);

        beforeUpdate(model);
        updateAction(model);
        afterUpdate(model);
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
    @ResponseStatus(value = NO_CONTENT, code = NO_CONTENT)
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
