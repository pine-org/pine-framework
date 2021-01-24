package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.BatchService;
import com.pineframework.core.datamodel.exception.ValidationException;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.validation.CreateValidationGroup;
import com.pineframework.core.datamodel.validation.UpdateValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.collection.Iterator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

import static com.pineframework.core.helper.CollectionUtils.toArray;
import static com.pineframework.core.spring.restapi.helper.ErrorUtils.checkErrors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.MULTI_STATUS;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * expose batch restful web services
 * the endpoints never should be overridden
 *
 * @param <I> id
 * @param <M> transient model
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends BatchService<I, M>>
        extends Rest<S> {

    /**
     * expose batch save, update and delete restful web service on the HTTP at the one operation.
     * {@code batchOperations} never should be overridden.
     *
     * @param models     new data also the data should be update
     * @param identities identities of data that fetched already
     * @param errors     validation errors
     * @return arrays of ID
     * @throws {@link ValidationException} if {@code models} has invalid data
     */
    @Operation(summary = "${restfulApi.batchOperations.summary}",
            description = "${restfulApi.batchOperations.description}",
            method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "207", description = "${restfulApi.batchOperations.response.207}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.batchOperations.response.422}")})
    @PutMapping("/batch/operations")
    @ResponseStatus(value = MULTI_STATUS, code = MULTI_STATUS)
    default ResponseEntity<CollectionModel<I>> batchOperations(
            @Parameter(name = "Models", description = "${restfulApi.batchOperations.param.1st}", required = true)
            @Validated({CreateValidationGroup.class, UpdateValidationGroup.class})
            @RequestBody List<M> models,
            @Parameter(name = "Identities", description = "${restfulApi.batchOperations.param.2nd}", required = true)
            @Valid
            @RequestBody I[] identities,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        M[] array = toArray(models, this.getClass(), 1);

        beforeBatchOperations(array);
        I[] ids = batchOperationsBody(array, identities);
        afterBatchOperations(array);

        Link[] links = Stream.of(ids)
                .map(id -> linkTo(getClass(), id).slash(id).withSelfRel())
                .toArray(Link[]::new);

        return ResponseEntity.status(CREATED).body(new CollectionModel(Iterator.of(ids), Iterator.of(links)));
    }

    /**
     * execute before batch operation.
     * overridable.
     *
     * @param models
     */
    default void beforeBatchOperations(M[] models) {
    }

    /**
     * execute batch operation logic.
     * overridable.
     *
     * @param model
     * @param identities
     * @return array of ID
     */
    default I[] batchOperationsBody(M[] model, I[] identities) {
        return getService().batchOperations(model, identities);
    }

    /**
     * execute after batch operation.
     * overridable.
     *
     * @param models
     */
    default void afterBatchOperations(M[] models) {
    }

    /**
     * expose batch save restful web service on the HTTP.
     * {@code batchSave} never should be overridden.
     *
     * @param models
     * @param errors validation errors
     * @return arrays of ID
     * @throws {@link ValidationException} if {@code models} has invalid data
     */
    @Operation(summary = "${restfulApi.batchSave.summary}",
            description = "${restfulApi.batchSave.description}",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${restfulApi.batchSave.response.201}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.batchSave.response.422}")})
    @PostMapping("/batch/save")
    @ResponseStatus(value = CREATED, code = CREATED)
    default ResponseEntity<CollectionModel<I>> batchSave(
            @Parameter(name = "Model", description = "${restfulApi.batchSave.param}", required = true)
            @Validated(CreateValidationGroup.class)
            @RequestBody List<M> models,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        M[] array = toArray(models, this.getClass(), 1);

        beforeBatchSave(array);
        I[] ids = batchSaveBody(array);
        afterBatchSave(array);

        Link[] links = Stream.of(ids)
                .map(id -> linkTo(getClass(), id).slash(id).withSelfRel())
                .toArray(Link[]::new);

        return ResponseEntity.status(CREATED).body(new CollectionModel(Iterator.of(ids), Iterator.of(links)));

    }

    /**
     * execute before batch save operation.
     * overridable.
     *
     * @param models
     */
    default void beforeBatchSave(M[] models) {
    }

    /**
     * batch save operation.
     * overridable.
     *
     * @param model
     * @return array of ID
     */
    default I[] batchSaveBody(M[] model) {
        return getService().batchSave(model);
    }

    /**
     * execute after batch save operation.
     * overridable.
     *
     * @param models
     */
    default void afterBatchSave(M[] models) {
    }

    /**
     * expose batch save restful web service on the HTTP.
     * {@code batchSave} never should be overridden.
     *
     * @param models
     * @param errors validation errors
     * @return arrays of ID
     * @throws {@link ValidationException} if {@code models} has invalid data
     */
    @Operation(summary = "${restfulApi.batchUpdate.summary}",
            description = "${restfulApi.batchUpdate.description}",
            method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${restfulApi.batchUpdate.response.204}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.batchUpdate.response.422}")})

    @PutMapping("/batch/update")
    @ResponseStatus(value = NO_CONTENT, code = NO_CONTENT)
    default ResponseEntity batchUpdate(
            @Parameter(name = "Model", description = "${restfulApi.batchUpdate.param}", required = true)
            @Validated(UpdateValidationGroup.class)
            @RequestBody List<M> models,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        checkErrors(errors);

        M[] array = toArray(models, this.getClass(), 1);

        beforeBatchUpdate(array);
        batchUpdateBody(array);
        afterBatchUpdate(array);

        return ResponseEntity.noContent().build();
    }

    /**
     * execute before batch update operation.
     * overridable.
     *
     * @param model
     */
    default void beforeBatchUpdate(M[] model) {
    }

    /**
     * batch update operation.
     * overridable.
     *
     * @param model
     */
    default void batchUpdateBody(M[] model) {
        getService().batchUpdate(model);
    }

    /**
     * execute before batch update operation.
     * overridable.
     *
     * @param model
     */
    default void afterBatchUpdate(M[] model) {
    }

    /**
     * expose batch delete restful web service on the HTTP.
     * {@code batchDelete} never should be overridden.
     *
     * @param identities
     * @return
     * @throws {@link ValidationException} if {@code models} has invalid data
     */
    @Operation(summary = "${restfulApi.batchDelete.summary}",
            description = "${restfulApi.batchDelete.description}",
            method = "DELETE")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "${restfulApi.batchDelete.response.204}")})
    @DeleteMapping("/batch/delete/{identities}")
    @ResponseStatus(value = NO_CONTENT, code = NO_CONTENT)
    default ResponseEntity batchDelete(
            @Parameter(name = "identities", description = "${restfulApi.batchDelete.param}", required = true)
            @PathVariable("identities") I[] identities) {

        batchDeleteBody(identities);

        return ResponseEntity.noContent().build();
    }

    /**
     * batch delete operation.
     * overridable.
     *
     * @param identities
     */
    default void batchDeleteBody(I[] identities) {
        getService().batchDelete(identities);
    }

}
