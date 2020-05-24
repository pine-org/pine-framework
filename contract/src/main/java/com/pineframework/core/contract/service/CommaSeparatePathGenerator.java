package com.pineframework.core.contract.service;

import com.pineframework.core.datamodel.persistence.TreePersistence;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * Generate unique path for nodes of a tree
 * use 'id' as a part of path and 'comma' as a separator
 *
 * @param <I> <description>id</description>
 * @param <E> <description>entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Component(CommaSeparatePathGenerator.BEAN_NAME)
public class CommaSeparatePathGenerator<I extends Serializable, E extends TreePersistence<I, E>>
        implements PathGeneratorStrategy<I, E> {

    public static final String BEAN_NAME = "commaSeparatePathGenerator";

    /**
     * set path to the entity
     *
     * @param entity
     * @param path
     */
    @Override
    public void setPath(E entity, String path) {
        entity.setPath(format("%s%s", (nonNull(path) ? path + "," : ""), entity.getId()));
    }
}
