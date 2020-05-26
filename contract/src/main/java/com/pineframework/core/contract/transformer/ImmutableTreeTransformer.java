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
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>,
        B extends TreeTransient.Builder<I, M, B>>
        extends ImmutableFlatTransformer<I, M, E, B> {

    M hierarchyTransform(E e, int level, int deep, String... fields);

    M hierarchyTransform(E e, String... fields);
}
