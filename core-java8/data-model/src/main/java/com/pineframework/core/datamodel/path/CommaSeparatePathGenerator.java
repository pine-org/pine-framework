package com.pineframework.core.datamodel.path;

import com.pineframework.core.datamodel.persistence.TreePersistence;

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
public class CommaSeparatePathGenerator<I extends Serializable, E extends TreePersistence<I, E>>
        implements PathGenerator<I, E> {

    public static final String BEAN_NAME = "commaSeparatePathGenerator";

    /**
     * set path to the entity
     *
     * @param entity
     * @param parentPath
     */
    @Override
    public void updatePath(E entity, String parentPath) {
        if (nonNull(parentPath))
            entity.setPath(format("%s,%s", parentPath, entity.getParent().getId()));
        else
            entity.setPath(format("%s", entity.getParent().getId()));
    }
}
