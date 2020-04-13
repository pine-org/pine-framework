package com.pineframework.core.contract.transformer;

import com.pineframework.core.datastructure.model.TreeTransient;
import com.pineframework.core.datastructure.persistence.TreePersistence;

import java.io.Serializable;
import java.util.List;


/**
 * Use for a table and value object designed in tree structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TreeTransformer<
        I extends Serializable,
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>>
        extends Transformer<I, M, E> {

    List<M> lazyTransform(List<E> entities, int deep, String... fields);

    M lazyTransform(E e, int deep, String... fields);

    List<M> lazyTransform(List<E> entities, String... fields);

    M lazyTransform(E e, String... fields);

    M hierarchyTransform(E e, int level, int deep, String... fields);

    M hierarchyTransform(E e, String... fields);
}
