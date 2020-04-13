package com.pineframework.core.helper;

import com.pineframework.core.datastructure.persistence.TreePersistence;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.pineframework.core.datastructure.model.optional.BooleanOptional.check;
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
        implements PathGenerator<I, E> {

    public static final String BEAN_NAME = "commaSeparatePathGenerator";

    /**
     * set path to the entity
     *
     * @param entity
     * @param parentPath
     */
    @Override
    public void setPath(E entity, String parentPath) {
        check(nonNull(parentPath))
                .ifTrue(() -> entity.setPath(String.format("%s,%s", parentPath, entity.getParent().getId())))
                .other(() -> entity.setPath(String.format("%s", entity.getParent().getId())));
    }
}
