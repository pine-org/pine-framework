package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.contract.service.AroundServiceOperation;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.service.TreeService;
import com.pineframework.core.contract.transformer.ImmutableTreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.path.PathGenerator;
import com.pineframework.core.datamodel.persistence.TreePersistence;
import io.vavr.control.Option;

import java.io.Serializable;
import java.util.Optional;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static java.util.Objects.nonNull;

public interface TreeEntityService<I extends Serializable,
        E extends TreePersistence<I, E>,
        M extends TreeTransient<I, M>,
        B extends TreeTransient.Builder<I, M, B>,
        T extends ImmutableTreeTransformer<I, M, E, B>,
        R extends TreeRepository<I, E>>
        extends BusinessService<I, E, M, B, T, R>, AroundServiceOperation<I, E, M>, TreeService<I, M> {

    PathGenerator<I, E> getPathGenerator();

    @Override
    default void beforeSave(E e, M m) {
        updatePath(e);
    }

    @Override
    default void afterUpdate(E e, M theLast) {
        updatePath(e);
        updateChildrenPath(e);
    }

    default void updatePath(E e) {
        if (nonNull(e.getParent())) {
            E parent = getRepository().findTree(e.getParent().getId()).get();
            Match(Option.of(parent)).of(
                    Case($None(), run(() -> e.setPath("0"))),
                    Case($Some($()), run(() -> getPathGenerator().updatePath(e, parent.getPath()))));
        }
    }

    default void updateChildrenPath(E e) {
        e.getChildren().forEach(item -> {
            updatePath(item);
            updateChildrenPath(item);
        });
    }

    @Override
    default M[] findChildren(I id) {
        return getTransformer().transform(getRepository().findChildren(id));
    }

    @Override
    default M[] findTreeAsList(I id) {
        return getTransformer().transform(getRepository().findTreeAsList(id));
    }

    @Override
    default M[] findSubTreeAsList(I id) {
        return getTransformer().transform(getRepository().findSubTreeAsList(id));
    }

    @Override
    default Optional<M> findTree(I id) {
        E e = getRepository().findTree(id).orElseGet(() -> createEmptyPersistenceObject());
        return Optional.ofNullable(getTransformer().hierarchyTransform(e));
    }

}
