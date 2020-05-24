package com.pineframework.core.business.transformer;

import com.pineframework.core.contract.transformer.TreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

import static com.pineframework.core.helper.CollectionUtils.contains;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractTreeTransformer<I extends Serializable,
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>>
        extends AbstractFlatTransformer<I, M, E> implements TreeTransformer<I, M, E> {

    @Override
    public void addToEntity(E e, M m, int deep, String... fields) {
        super.addToEntity(e, m, deep, fields);

        if (nonNull(m.getParent()))
            e.setParent(transform(m.getParent(), deep, fields));

        e.setPath(m.getPath());
    }

    @Override
    public void addToModel(E e, M m, int deep, String... fields) {
        super.addToModel(e, m, deep, fields);

        if (isNull(e) || (deep == EXIT))
            return;
        if (contains(fields, "parent"))
            transformParent(e, m, deep, fields);
        if (contains(fields, "path"))
            m.setPath(e.getPath());

        m.setLeaf(e.isLeaf());
    }

    public void transformParent(E e, M m, int deep, String[] fields) {
        M parent = null;

        if (nonNull(e.getParent()))
            parent = transform(e.getParent(), deep--, fields);

        if (deep == EXIT)
            parent = createModel(e.getParent().getId(), e.getParent().getVersion());

        m.setParent(parent);
    }

    @Override
    public M hierarchyTransform(E e, int level, int deep, String... fields) {
        if (isNull(e) || (level == EXIT)) return null;

        M model = transform(e, deep, fields);
        model.setChildren(mapTo(e.getChildren(), entity -> hierarchyTransform(entity, level - 1, deep, fields)));
        return model;
    }

    @Override
    public M hierarchyTransform(E e, String... fields) {
        return hierarchyTransform(e, END_DEEP, FIRST_DEEP, fields);
    }
}
