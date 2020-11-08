package com.pineframework.core.contract.transformer;

import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;


/**
 * Provide transformer operation for hierarchy structure.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
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
