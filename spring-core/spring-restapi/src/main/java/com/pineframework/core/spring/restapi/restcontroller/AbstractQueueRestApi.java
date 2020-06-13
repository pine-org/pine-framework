package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.restapi.CreateValidationGroup;
import com.pineframework.core.contract.service.queue.QueueService;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.spring.restapi.helper.ErrorUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<I> save(
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
    public void beforeSave(M model) {
    }

    /**
     * execute after save operation
     *
     * @param model
     */
    public void afterSave(M model) {
    }

    /**
     * save operation
     * overridable
     *
     * @param model
     * @return ID
     */
    public ResponseEntity<I> saveAction(M model) {
        return ResponseEntity.status(CREATED).body(getService().save(model).get().getId());
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
    public ResponseEntity<M> findById(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findById.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        return ok(getService().findById(id).get());
    }

}
