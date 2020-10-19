package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.paging.PageMetadataView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import static com.pineframework.core.helper.JsonUtils.asString;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
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

public interface PagingRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends QueryService<I, M>>
        extends Rest<S> {

    /**
     * expose find data with pagination service on http
     * never override
     *
     * @param page
     * @return list of value objects
     */
    @ApiOperation(value = "${restfulApi.page.value}", notes = "${restfulApi.page.notes}")
    @GetMapping("search/page/{page}")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<EntityModel<Page>> findPage(
            @ApiParam(name = "Page", value = "${restfulApi.page.param}", required = true)
            @PathVariable(value = "page") Page page) {

        String currentPageMetadata = asString(page, PageMetadataView.class);
        return ok(new EntityModel<>(getService().findByPage(page),
                linkTo(getClass()).slash("search")
                        .slash("page")
                        .slash(currentPageMetadata)
                        .withSelfRel(),
                linkTo(getClass()).slash("search")
                        .slash("page")
                        .slash(asString(page, PageMetadataView.class))
                        .withRel("next")
        ));
    }

}
