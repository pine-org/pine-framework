package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.TreeService;
import com.pineframework.core.datamodel.model.TreeTransient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.collection.Iterator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.stream.Stream;

import static com.pineframework.core.business.helper.ObjectUtils.requiredNonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.ok;

/**
 * expose tree restful web services
 * the endpoints never should be overridden
 *
 * @param <I> id
 * @param <M> transient model
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface TreeRestfulApi<I extends Serializable, M extends TreeTransient<I, M>, S extends TreeService<I, M>>
        extends Rest<S> {

    /**
     * expose find tree by id restfull web service on http
     * {@code findById} never should be overridden
     *
     * @param id
     * @return Json model with tree structure
     */
    @Operation(summary = "${restfulApi.findTree.summary}",
            description = "${restfulApi.findTree.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findTree.response.200}")})
    @GetMapping("find/tree/{id}")
    default ResponseEntity<EntityModel<M>> findTree(
            @Parameter(name = "ID", description = "${restfulApi.findTree.param}", required = true)
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");

        return getService().findTree(id)
                .map(m -> new EntityModel<>(m, linkTo(getClass()).slash(m.getId()).withSelfRel()))
                .map(ResponseEntity::ok)
                .get();
    }

    /**
     * expose find children by id restfull web service on http
     * {@code findById} never should be overridden
     *
     * @param id
     * @return an array of Json model
     */
    @Operation(summary = "${restfulApi.findChildren.summary}",
            description = "${restfulApi.findChildren.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findChildren.response.200}")})
    @GetMapping(value = {"find/tree/children/{id}", "find/tree/children"})
    default ResponseEntity<CollectionModel<M>> findChildren(
            @Parameter(name = "ID", description = "${restfulApi.findChildren.param}", required = false)
            @PathVariable(value = "id", required = false) I id) {

        requiredNonNull(id, "id");

        M[] children = getService().findChildren(id);

        Link[] links = Stream.of(children)
                .map(item -> linkTo(getClass()).slash(item.getId()).withSelfRel())
                .toArray(Link[]::new);

        return ok(new CollectionModel<>(Iterator.of(children), Iterator.of(links)));
    }

    /**
     * expose find tree as a restfull web service on http
     * {@code findById} never should be overridden
     *
     * @param id
     * @return an array of Json model
     */
    @Operation(summary = "${restfulApi.findTreeAsList.summary}",
            description = "${restfulApi.findTreeAsList.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findTreeAsList.response.200}")})
    @GetMapping("find/tree-list/{id}")
    default ResponseEntity<CollectionModel<M>> findTreeAsList(
            @Parameter(name = "ID", description = "${restfulApi.findTreeAsList.param}", required = true)
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");

        M[] nodes = getService().findTreeAsList(id);

        Link[] links = Stream.of(nodes)
                .map(item -> linkTo(getClass()).slash(item.getId()).withSelfRel())
                .toArray(Link[]::new);

        return ok(new CollectionModel<>(Iterator.of(nodes), Iterator.of(links)));
    }

    /**
     * expose find sub tree as a restfull web service on http
     * {@code findById} never should be overridden
     *
     * @param id
     * @return an array of Json model
     */
    @Operation(summary = "${restfulApi.findSubTreeAsList.summary}",
            description = "${restfulApi.findSubTreeAsList.description}",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${restfulApi.findSubTreeAsList.response.200}")})
    @GetMapping("find/sub-tree-list/{id}")
    default ResponseEntity<CollectionModel<M>> findSubTreeAsList(
            @Parameter(name = "ID", description = "${restfulApi.findSubTreeAsList.param}", required = true)
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");

        M[] children = getService().findSubTreeAsList(id);

        Link[] links = Stream.of(children)
                .map(item -> linkTo(getClass()).slash(item.getId()).withSelfRel())
                .toArray(Link[]::new);

        return ok(new CollectionModel<>(Iterator.of(children), Iterator.of(links)));
    }

}
