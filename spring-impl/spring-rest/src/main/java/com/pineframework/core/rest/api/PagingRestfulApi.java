package com.pineframework.core.rest.api;

import com.pineframework.core.contract.service.entityservice.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

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
     * @param paging
     * @return list of value objects
     */
    @ApiOperation(value = "${restfulApi.find.value}", notes = "${restfulApi.find.notes}")
    @PostMapping("find/paging")
    default ResponseEntity<Page> find(
            @ApiParam(name = "Model", value = "${restfulApi.page.param}", required = true)
            @Valid @RequestBody Page paging) {
        return ResponseEntity.ok(getService().findByPage(paging));
    }

}
