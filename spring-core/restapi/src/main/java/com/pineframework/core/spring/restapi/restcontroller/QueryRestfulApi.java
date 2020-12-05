package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.collection.Iterator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

/**
 * expose only find all and find by filter restful web services
 * the endpoints never should be overridden
 *
 * @param <I> id
 * @param <M> transient object
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface QueryRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends QueryService<I, M>>
        extends Rest<S> {

    /**
     * expose find all restful web service on http
     * {@code findAll} never should be overridden
     *
     * @return an array of JSON model
     */
    @Operation(summary = "${restfulApi.findAll.summary}",
            description = "${restfulApi.findAll.description}",
            method = "GET")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${restfulApi.findAll.response.200}")})
    @GetMapping("search/all")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<CollectionModel<M>> findAll() {
        M[] models = findAllBody();

        Link[] links = Stream.of(models)
                .map(item -> linkTo(getClass()).slash("search")
                        .slash("all")
                        .slash(item.getId())
                        .withSelfRel())
                .toArray(Link[]::new);

        return ok(new CollectionModel<>(Iterator.of(models), Iterator.of(links)));
    }

    default M[] findAllBody() {
        return getService().findAll();
    }

    /**
     * expose find by filter restful web service on http
     * {@code findByFilter} never should be overridden
     *
     * @param filters
     * @return an array of JSON model
     */
    @Operation(summary = "${restfulApi.findByFilter.summary}",
            description = "${restfulApi.findByFilter.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findByFilter.response.200}")})
    @PostMapping("search/filter/{filter}")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<CollectionModel<M>> findByFilter(
            @Parameter(name = "filter", description = "${restfulApi.findByFilter.param}", required = true)
            @PathVariable(value = "filter") Filter[] filters) {

        M[] models = findByFilterBody(filters);

        Link[] links = Stream.of(models)
                .map(item -> linkTo(getClass()).slash(item.getId()).withSelfRel())
                .toArray(Link[]::new);

        return ok(new CollectionModel<>(Iterator.of(models), Iterator.of(links)));

    }

    default M[] findByFilterBody(Filter[] filters) {
        return getService().findByFilter(filters);
    }

}
