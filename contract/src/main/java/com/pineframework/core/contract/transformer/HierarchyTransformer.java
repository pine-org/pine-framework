package com.pineframework.core.contract.transformer;

import com.pineframework.core.datastructure.model.TreeTransient;
import com.pineframework.core.datastructure.persistence.TreePersistence;

import java.io.Serializable;
import java.util.List;

import static com.pineframework.core.helper.CollectionUtils.EMPTY_LIST;
import static com.pineframework.core.helper.CollectionUtils.contains;
import static com.pineframework.core.helper.CollectionUtils.isEmpty;
import static com.pineframework.core.helper.CollectionUtils.map;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class HierarchyTransformer<I extends Serializable,
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>>
        extends FlatTransformer<I, M, E> implements TreeTransformer<I, M, E> {

    @Override
    public void addToEntity(E e, M m, int deep, String... fields) {
        super.addToEntity(e, m, deep, fields);
        if (nonNull(m.getParent())) e.setParent(transform(m.getParent(), deep, fields));
        e.setPath(m.getPath());
    }

    @Override
    public void addToModel(E e, M m, int deep, String... fields) {
        super.addToModel(e, m, deep, fields);

        if (isNull(e) || (deep == EXIT)) return;
        if (contains(fields, "parent")) transformParent(e, m, deep, fields);
        if (contains(fields, "path")) m.setPath(e.getPath());

        m.setLeaf(e.isLeaf());
    }

    public void transformParent(E e, M m, int deep, String[] fields) {
        M parent = null;

        if (nonNull(e.getParent())) {
            deep--;
            parent = transform(e.getParent(), deep, fields);
        }

        if (deep == 0)
            parent = createModel(e.getParent().getId(), e.getParent().getVersion());

        m.setParent(parent);
    }

    @Override
    public List<M> lazyTransform(List<E> entities, int deep, String... fields) {
        return isEmpty(entities) ? EMPTY_LIST : map(entities, entity -> lazyTransform(entity, deep, fields));
    }

    @Override
    public List<M> lazyTransform(List<E> entities, String... fields) {
        return lazyTransform(entities, FIRST_DEEP, fields);
    }

    @Override
    public M lazyTransform(E e, int deep, String... fields) {
        return transform(e, deep, fields);
    }

    @Override
    public M lazyTransform(E e, String... fields) {
        return lazyTransform(e, FIRST_DEEP, fields);
    }

    @Override
    public M hierarchyTransform(E e, int level, int deep, String... fields) {
        if (isNull(e) || (level == EXIT)) return null;

        M model = transform(e, deep, fields);
        model.setChildren(map(e.getChildren(), entity -> hierarchyTransform(entity, level - 1, deep, fields)));
        return model;
    }

    @Override
    public M hierarchyTransform(E e, String... fields) {
        return hierarchyTransform(e, END_DEEP, FIRST_DEEP, fields);
    }
}
