package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.ImmutableTreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.path.PathGenerator;
import com.pineframework.core.datamodel.persistence.TreePersistence;
import io.vavr.control.Option;

import java.io.Serializable;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static java.util.Objects.nonNull;

public interface TreeEntityService<I extends Serializable,
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>,
        R extends TreeRepository<I, E>,
        B extends TreeTransient.Builder<I, M, B>,
        T extends ImmutableTreeTransformer<I, M, E, B>>
        extends BusinessService<I, M, E, R, B, T>, AroundServiceOperation<I, M, E> {

    PathGenerator<I, E> getPathGenerator();

    default void afterUpdate(E e, M m) {
        setPath(e);
        setChildrenPath(e);
    }

    default void setChildrenPath(E e) {
        e.getChildren().forEach(item -> {
            setPath(item);
            setChildrenPath(item);
        });
    }

    @Override
    default void beforeSave(E e, M m) {
        setPath(e);
    }

    default void setPath(E e) {
        if (nonNull(e.getParent())) {
            E parent = getRepository().findTree(e.getParent().getId()).get();
            Match(Option.of(parent)).of(
                    Case($None(), run(() -> e.setPath("0"))),
                    Case($Some($()), run(() -> getPathGenerator().setPath(e, parent.getPath())))
            );
        }
    }

    default M[] findChildren(I id) {
        return getTransformer().transform(getRepository().findChildren(id));
    }

    default M[] findTreeAsList(I id) {
        return getTransformer().transform(getRepository().findTreeAsList(id));
    }

    default M[] findSubTreeAsList(I id) {
        return getTransformer().transform(getRepository().findSubTreeAsList(id));
    }

    default M findTree(I id) {
        E e = getRepository().findTree(id).orElseGet(() -> createEmptyPersistenceObject());
        return getTransformer().hierarchyTransform(e);
    }

}
