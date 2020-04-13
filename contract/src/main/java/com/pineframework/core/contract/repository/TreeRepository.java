package com.pineframework.core.contract.repository;

import com.pineframework.core.datastructure.persistence.TreePersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface TreeRepository extends FlatRepository {

    <I extends Serializable, E extends TreePersistence<I, E>> List<E> findChildren(I id, Class<E> c);

    <I extends Serializable, E extends TreePersistence<I, E>> List<E> findListTree(I id, Class<E> c);

    <I extends Serializable, E extends TreePersistence<I, E>> List<E> findListSubTree(I id, Class<E> c);

    <I extends Serializable, E extends TreePersistence<I, E>> Optional<E> findTree(I id, Class<E> c);

}
