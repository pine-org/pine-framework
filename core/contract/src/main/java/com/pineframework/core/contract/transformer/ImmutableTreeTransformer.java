package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

/**
 * Use for a table and value object designed in tree structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface ImmutableTreeTransformer<
        I extends Serializable,
        E extends TreePersistence<I, E>,
        M extends TreeTransient<I, M>,
        B extends TreeTransient.Builder<I, M, B>>
        extends ImmutableFlatTransformer<I, E, M, B> {

    M hierarchyTransform(E e, int level, int deep, String... fields);

    M hierarchyTransform(E e, String... fields);
}
