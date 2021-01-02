package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.exception.ValidationException;
import com.pineframework.core.datamodel.model.EmptyModel;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.validation.CreateValidationGroup;
import com.pineframework.core.spring.restapi.helper.ErrorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import static io.vavr.API.Function;
import static io.vavr.API.Option;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

/**
 * It provides two endpoints to interact with a queue.
 * Push a message to the queue also get the message if there is no listener to consume the message.
 * the endpoints never should be overridden
 *
 * @param <I> correlation ID
 * @param <M> message model
 * @param <S> queue business service
 * @author Saman Alishirishahrbabak
 */
public abstract class AbstractQueueRestApi<I extends Serializable, M extends FlatTransient<I>,
        S extends QueueService<I, M>> implements Rest<S> {

    protected S service;

    protected AbstractQueueRestApi(S service) {
        this.service = service;
    }

    @Override
    public S getService() {
        return service;
    }

    /**
     * expose push message service on http over the POST verb.
     * {@code push} never should be overridden.
     *
     * @param model  message model
     * @param errors validation errors
     * @return correlation ID
     * @throws {@link ValidationException} if {@code models} has invalid data
     */
    @Operation(summary = "${restfulApi.push.summary}",
            description = "${restfulApi.push.description}",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "207", description = "${restfulApi.push.response.201}"),
            @ApiResponse(responseCode = "422", description = "${restfulApi.push.response.422}")})
    @PostMapping
    @ResponseStatus(value = CREATED, code = CREATED)
    public ResponseEntity<EntityModel<M>> push(
            @Parameter(name = "Model", description = "${restfulApi.push.param}", required = true)
            @Validated(CreateValidationGroup.class)
            @RequestBody M model,
            @Parameter(name = "errors", hidden = true) Errors errors) {

        ErrorUtils.checkErrors(errors);

        return Option(Function(this::pushBody)
                .compose(this::beforePush)
                .andThen(this::afterPush)
                .apply(model))
                .map(m -> new EntityModel<>(m, linkTo(getClass(), m.getId()).slash(m.getId()).withSelfRel()))
                .map(m -> ResponseEntity.status(CREATED).body(m))
                .get();
    }

    /**
     * execute before push operation.
     * overridable.
     *
     * @param model message
     * @return model message
     */
    protected M beforePush(M model) {
        return model;
    }

    /**
     * push operation logic.
     * overridable.
     *
     * @param model
     * @return model
     */
    protected M pushBody(M model) {
        return getService().push(model).orElse((M) new EmptyModel.Builder().build());
    }

    /**
     * execute after push operation.
     * overridable.
     *
     * @param model
     * @return model
     */
    protected M afterPush(M model) {
        return model;
    }

    /**
     * expose push message service on http over the POST verb.
     * {@code findByCorrelationId} never should be overridden.
     *
     * @param id correlation ID
     * @return message model
     */
    @Operation(summary = "${restfulApi.findByCorrelationId.summary}",
            description = "${restfulApi.findByCorrelationId.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findByCorrelationId.response.200}")})
    @GetMapping("/{id}")
    @ResponseStatus(value = OK, code = OK)
    public ResponseEntity<EntityModel<M>> findByCorrelationId(
            @Parameter(name = "ID", description = "${restfulApi.findByCorrelationId.param}", required = true)
            @PathVariable("id") I id) {

        return ok(Option(getService().findByCorrelationId(id))
                .map(m -> new EntityModel<>(m.get(), linkTo(getClass(), m.get()).slash(m.get()).withSelfRel()))
                .get());
    }

}
