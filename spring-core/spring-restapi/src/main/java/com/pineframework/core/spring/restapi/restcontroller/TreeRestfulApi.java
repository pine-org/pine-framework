package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.TreeService;
import com.pineframework.core.datamodel.model.TreeTransient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

import static com.pineframework.core.business.helper.ObjectUtils.requiredNonNull;

/**
 * exposed tree services
 *
 * @param <I> id
 * @param <M> value object
 * @param <S> business service
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface TreeRestfulApi<I extends Serializable, M extends TreeTransient<I, M>, S extends TreeService<I, M>>
        extends Rest<S> {

    /**
     * get node and children of the node in different level as a tree structure on http
     *
     * @param id
     * @return list of value object
     */
    @ApiOperation(value = "${restfulApi.findTree.value}", notes = "${restfulApi.findTree.notes}")
    @GetMapping("find/tree/{id}")
    default ResponseEntity<M> findTree(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findTree.param}",
                    required = true, defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");
        return ResponseEntity.ok(getService().findTree(id));
    }

    /**
     * get children of the node in one level as a list on http
     *
     * @param id
     * @return list of value object
     */
    @ApiOperation(value = "${restfulApi.findChildren.value}", notes = "${restfulApi.findChildren.notes}")
    @GetMapping(value = {"find/children/{id}", "find/children"})
    default ResponseEntity<M[]> findChildren(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findChildren.param}",
                    required = false,
                    defaultValue = "0",
                    example = "0")
            @PathVariable(value = "id", required = false) I id) {

        requiredNonNull(id, "id");
        return ResponseEntity.ok(getService().findChildren(id));
    }

    /**
     * get node and children of the node in different level as a list on http
     *
     * @param id
     * @return list of value object
     */
    @ApiOperation(value = "${restfulApi.findListTree.value}", notes = "${restfulApi.findListTree.notes}")
    @GetMapping("find/tree-list/{id}")
    default ResponseEntity<M[]> findTreeAsList(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findListTree.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");
        return ResponseEntity.ok(getService().findTreeAsList(id));
    }

    /**
     * get children of the node in different level as a list on http
     *
     * @param id
     * @return list of value object
     */
    @ApiOperation(value = "${restfulApi.findListSubTree.value}", notes = "${restfulApi.findListSubTree.notes}")
    @GetMapping("find/sub-tree-list/{id}")
    default ResponseEntity<M[]> findSubTreeAsList(
            @ApiParam(name = "ID",
                    value = "${restfulApi.findListSubTree.param}",
                    required = true,
                    defaultValue = "0",
                    example = "0")
            @PathVariable("id") I id) {

        requiredNonNull(id, "id");
        return ResponseEntity.ok(getService().findSubTreeAsList(id));
    }

}
