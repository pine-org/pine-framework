package com.pineframework.core.rest.api;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.io.Serializable;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

/**
 * exposed paging and read only services
 *
 * @param <I> id
 * @param <M> value object
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends QueryService<I, M>>
        extends Rest<S> {

    /**
     * expose find all service on http
     * never override
     *
     * @return list of value objects
     */
    @ApiOperation(value = "${restfulApi.findAll.value}", notes = "${restfulApi.findAll.notes}")
    @GetMapping("search/all")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<M[]> findAll() {
        return findAllAction();
    }

    default ResponseEntity<M[]> findAllAction() {
        return ok(getService().findAll());
    }

    @ApiOperation(value = "${restfulApi.find.value}", notes = "${restfulApi.find.notes}")
    @PostMapping("search/filter")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<M[]> findByFilter(
            @ApiParam(name = "Value Object", value = "${restfulApi.save.param}", required = true)
            @Valid @RequestBody Filter[] filters) {
        return findByFilterAction(filters);
    }

    default ResponseEntity<M[]> findByFilterAction(Filter[] filters) {
        return ok(getService().findByFilter(filters));
    }

}
