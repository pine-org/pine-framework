package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.datamodel.paging.Pageable;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.datamodel.select.Select;
import com.pineframework.core.datamodel.select.SelectAll;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import static com.pineframework.core.helper.CollectionUtils.ofNullable;
import static java.util.Optional.ofNullable;

/**
 * Independent implementation of repository based on JPA for simple flat table
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

@Repository("jpaRepositoryImpl")
public class JpaFlatRepositoryImpl implements JpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getStorageSession() {
        return entityManager;
    }

    @Override
    public int getBatchSize() {
        return 10;
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>> void save(Class<E> c, E... entities) {
        for (int i = 0; i < entities.length; i++) {
            if (i > 0 && i % getBatchSize() == 0)
                getStorageSession().flush();
            getStorageSession().persist(entities[i]);
        }
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>> void delete(Class<E> c, I... identities) {
        CriteriaBuilder cb = getStorageSession().getCriteriaBuilder();
        CriteriaDelete<E> cq = cb.createCriteriaDelete(c);
        Root<E> root = cq.from(c);
        cq.where(root.get("id").in(identities));

        getStorageSession().createQuery(cq).executeUpdate();
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>> Optional<E> findOne(Class<E> c, I id) {
        return ofNullable(getStorageSession().find(c, id));
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>, M> Optional<M> findOne(Select<E, M> select) {
        return execute(select).getResultStream().findFirst();
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>, M> M[] find(Select<E, M> select) {
        TypedQuery<M> query = execute(select);
        List<M> list = query.getResultList();
        return ofNullable(list.toArray((M[]) Array.newInstance(select.getModelType(), list.size())));
    }

    @Override
    public <I extends Serializable, E extends FlatPersistence<I>> E[] paging(Class<E> c, Pageable page) {
        return execute(c, page);
    }

    public <I extends Serializable, E extends FlatPersistence<I>> E[] execute(Class<E> c, Pageable page) {
        TypedQuery<E> query = execute(new SelectAll<E>(c));
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getLimit());
        List<E> list = query.getResultList();
        return ofNullable(list.toArray((E[]) Array.newInstance(c, list.size())));
    }

    public <I extends Serializable, E extends FlatPersistence<I>, M> TypedQuery<M> execute(Select<E, M> select) {
        CriteriaBuilder cb = getStorageSession().getCriteriaBuilder();
        CriteriaQuery<M> cq = cb.createQuery(select.getModelType());
        Root<E> root = cq.from(select.getEntityType());
        cq.select(select.getSelection().apply(root, cb));
        select.getWhereClause().accept(cq, filter -> filter.toPredicate(root, cq, cb));
        TypedQuery<M> query = getStorageSession().createQuery(cq);
        query.setHint("org.hibernate.cacheable", true);
        return query;
    }

}
