package com.pineframework.core.business.transformer;

import com.pineframework.core.contract.transformer.ImmutableTreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

import static com.pineframework.core.helper.CollectionUtils.contains;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractImmutableTreeTransformer<I extends Serializable,
        E extends TreePersistence<I, E>,
        M extends TreeTransient<I, M>,
        B extends TreeTransient.Builder<I, M, B>>
        extends AbstractImmutableFlatTransformer<I, E, M, B> implements ImmutableTreeTransformer<I, E, M, B> {

    @Override
    public abstract B getModelBuilder(E e);

    @Override
    public void transformAdditionalToEntity(E e, M m, int deep, String... fields) {
        super.transformAdditionalToEntity(e, m, deep, fields);

        if (nonNull(m.getParent()))
            e.setParent(transform(m.getParent(), deep, fields));

        e.setPath(m.getPath());
    }

    @Override
    public void transformAdditionalToModel(E e, B builder, int deep, String... fields) {
        super.transformAdditionalToModel(e, builder, deep, fields);

        if (isNull(e) || (deep == EXIT))
            return;
        if (contains(fields, "parent"))
            transformParent(e, builder, deep, fields);
        if (contains(fields, "path"))
            builder.path(e.getPath());

        builder.isLeaf(e.isLeaf());
    }

    public void transformParent(E e, B builder, int deep, String[] fields) {
        M parent = null;

        if (nonNull(e.getParent()))
            parent = transform(e.getParent(), deep--, fields);
        if (deep == EXIT)
            parent = getModelBuilder(e).id(e.getId()).version(e.getVersion()).build();

        builder.parent(parent);
    }

    @Override
    public M hierarchyTransform(E e, int level, int deep, String... fields) {
        if (isNull(e) || (level == EXIT))
            return null;

        return getModelBuilder(e)
                .children(mapTo(e.getChildren(), entity -> hierarchyTransform(entity, level - 1, deep, fields)))
                .from(transform(e, deep, fields))
                .build();
    }

    @Override
    public M hierarchyTransform(E e, String... fields) {
        return hierarchyTransform(e, END_DEEP, FIRST_DEEP, fields);
    }
}
