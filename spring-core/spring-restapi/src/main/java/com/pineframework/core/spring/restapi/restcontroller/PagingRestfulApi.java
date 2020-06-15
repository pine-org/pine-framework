package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
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

public interface PagingRestfulApi<I extends Serializable, M extends FlatTransient<I>, S extends QueryService<I, M>>
        extends Rest<S> {

    /**
     * expose find data with pagination service on http
     * never override
     *
     * @param paging
     * @return list of value objects
     */
    @ApiOperation(value = "${restfulApi.page.value}", notes = "${restfulApi.page.notes}")
    @PostMapping("search/page")
    @ResponseStatus(value = OK, code = OK)
    default ResponseEntity<Page> findPage(
            @ApiParam(name = "Model", value = "${restfulApi.page.param}", required = true)
            @Valid @RequestBody Page paging) {
        return ok(getService().findByPage(paging));
    }

}
