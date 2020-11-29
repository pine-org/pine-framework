package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.enums.RequestParameter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.paging.PageMetadataView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.pineframework.core.helper.JsonUtils.asString;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.OK;

/**
 * expose paging and read only restful web services
 * the endpoints never should be overridden
 *
 * @param <I> id
 * @param <M> transient model
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface PagingRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends QueryService<I, M>>
        extends Rest<S> {

    /**
     * expose find data with pagination restful web service on http
     * {@code findPage} never should be overridden
     *
     * @param page
     * @return list of models as a page
     */
    @Operation(summary = "${restfulApi.page.summary}",
            description = "${restfulApi.page.description}",
            method = "GET")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${restfulApi.page.response.200}")})
    @GetMapping("search/page/{page}")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<EntityModel<Page>> findPage(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "Page", description = "${restfulApi.page.param}", required = true)
            @PathVariable(value = "page") Page page) {

        HashMap<String, Object> params = new HashMap<>();
        if (headers.containsKey("commands"))
            params.put(RequestParameter.COMMANDS.name(), headers.get("commands"));

        return getService().findByPage(page, params)
                .map(m -> new EntityModel<>(m, generatePageLinks(m)))
                .map(ResponseEntity::ok)
                .get();
    }

    default Link[] generatePageLinks(Page value) {
        return new Link[]{
                linkTo(getClass()).slash("search")
                        .slash("page")
                        .slash(asString(value, PageMetadataView.class))
                        .withSelfRel(),
                linkTo(getClass()).slash("search")
                        .slash("page")
                        .slash(asString(value.next(), PageMetadataView.class))
                        .withRel("next"),
                linkTo(getClass()).slash("search")
                        .slash("page")
                        .slash(asString(value.previous(), PageMetadataView.class))
                        .withRel("previous")};
    }
}
