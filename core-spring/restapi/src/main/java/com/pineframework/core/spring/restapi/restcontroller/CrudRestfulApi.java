package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.datamodel.exception.ValidationException;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.validation.CreateValidationGroup;
import com.pineframework.core.datamodel.validation.UpdateValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Optional;

import static com.pineframework.core.spring.restapi.helper.ErrorUtils.checkErrors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * expose CRUD restful web services
 * the endpoints never should be overridden
 *
 * @param <I> id
 * @param <M> transient model
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends CrudService<I, M>>
        extends Rest<S> {

    /**
     * expose save restfull web service on http
     * {@code save} never should be overridden
     *
     * @param model
     * @param errors
     * @return ID
     * @throws {@link ValidationException} if {@code model} has invalid data
     */
    @Operation(summary = "${restfulApi.save.summary}",
            description = "${restfulApi.save.description}",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${restfulApi.save.response.201}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.save.response.422}")})
    @PostMapping
    @ResponseStatus(value = CREATED, code = CREATED)
    default ResponseEntity save(
            @Parameter(name = "Model", description = "${restfulApi.save.param}", required = true)
            @Validated(CreateValidationGroup.class)
            @RequestBody M model,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);
        beforeSave(model);
        Optional<I> result = saveBody(model);
        afterSave(model);

        return result.map(id -> ResponseEntity.created(linkTo(getClass()).slash(id).toUri()).build()).get();
    }

    /**
     * execute before save operation.
     * overridable.
     *
     * @param model
     */
    default void beforeSave(M model) {
    }

    /**
     * save operation.
     * overridable.
     *
     * @param model
     * @return ID
     */
    default Optional<I> saveBody(M model) {
        return getService().save(model);
    }

    /**
     * execute after save operation
     * overridable.
     *
     * @param model
     */
    default void afterSave(M model) {
    }

    /**
     * expose find by id restfull web service on http
     * {@code findById} never should be overridden
     *
     * @param id
     * @return JSON model
     */
    @Operation(summary = "${restfulApi.findById.summary}",
            description = "${restfulApi.findById.description}",
            method = "GET")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${restfulApi.findById.response.200}")})
    @GetMapping("/{id}")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<EntityModel<M>> findById(
            @Parameter(name = "ID", description = "${restfulApi.findById.param}", required = true)
            @PathVariable("id") I id) {
        return getService().findByIdErrorProne(id)
                .map(m -> new EntityModel<>(m, linkTo(getClass()).slash(m.getId()).withSelfRel()))
                .map(ResponseEntity::ok)
                .get();
    }

    /**
     * expose update restfull web service on http
     * {@code update} never should be overridden
     *
     * @param model
     * @param errors
     * @throws {@link ValidationException} if {@code model} has invalid data
     */
    @Operation(summary = "${restfulApi.update.summary}",
            description = "${restfulApi.update.description}",
            method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${restfulApi.update.response.204}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.update.response.422}")})
    @PutMapping("/{id}")
    @ResponseStatus(value = NO_CONTENT, code = NO_CONTENT)
    default ResponseEntity update(
            @Parameter(name = "ID", description = "${restfulApi.findById.param}", required = true)
            @PathVariable("id") I id,
            @Parameter(name = "Model", description = "${restfulApi.update.param}", required = true)
            @Validated(UpdateValidationGroup.class)
            @RequestBody M model,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        beforeUpdate(id, model);
        updateBody(id, model);
        afterUpdate(id, model);
        return ResponseEntity.noContent().build();
    }

    /**
     * execute before update operation.
     * overridable.
     *
     * @param id
     * @param model
     */
    default void beforeUpdate(I id, M model) {
    }

    /**
     * update operation.
     * overridable.
     *
     * @param id
     * @param model
     */
    default void updateBody(I id, M model) {
        getService().update(id, model);
    }

    /**
     * execute before update operation.
     * overridable
     *
     * @param id
     * @param model
     */
    default void afterUpdate(I id, M model) {
    }

    /**
     * expose delete restfull web service on http
     * {@code delete} never should be overridden
     *
     * @param id
     */
    @Operation(summary = "${restfulApi.delete.summary}",
            description = "${restfulApi.delete.description}",
            method = "DELETE")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "${restfulApi.delete.response.204}")})
    @DeleteMapping("/{id}")
    @ResponseStatus(value = NO_CONTENT, code = NO_CONTENT)
    default ResponseEntity delete(
            @Parameter(name = "ID", description = "${restfulApi.delete.param}", required = true)
            @PathVariable("id") I id) {

        deleteBody(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * delete operation
     * overridable
     *
     * @param id
     */
    default void deleteBody(I id) {
        getService().delete(id);
    }
}
