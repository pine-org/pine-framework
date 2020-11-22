package com.pineframework.core.mongodb;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pineframework.core.mongodb.HibernateOgmUtils.createEntityManager;
import static com.pineframework.core.mongodb.HibernateOgmUtils.getJtaImpl;
import static java.util.Objects.nonNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class CategoryOgmRepository {

    public static final CategoryOgmRepository INSTANCE = new CategoryOgmRepository();

    private final Logger logger = LoggerFactory.getLogger(CategoryOgmRepository.class);

    private final TransactionManager tx = getJtaImpl();

    private CategoryOgmRepository() {
    }

    public Long insert(CategoryEntity category) {
        EntityManager em = null;

        try {
            tx.begin();
            em = createEntityManager();
            em.persist(category);
            tx.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
            Try.run(tx::rollback);

        } finally {
            if (nonNull(em))
                em.close();
        }

        return category.getId();
    }


    public Optional<CategoryEntity> selectById(Long id) {
        EntityManager em = null;
        CategoryEntity entity = null;

        try {
            tx.begin();
            em = createEntityManager();
            entity = em.find(CategoryEntity.class, id);
            tx.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
            Try.run(tx::rollback);

        } finally {
            if (nonNull(em))
                em.close();
        }

        return Optional.ofNullable(entity);
    }

    public List<CategoryEntity> selectAll() {
        EntityManager em = null;
        List<CategoryEntity> entities = Collections.emptyList();

        try {
            tx.begin();
            em = createEntityManager();
            entities = em.createQuery("SELECT e FROM CategoryEntity e ", CategoryEntity.class)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
            Try.run(tx::rollback);

        } finally {
            if (nonNull(em))
                em.close();
        }

        return entities;
    }

    public Boolean deleteById(Long id) {
        EntityManager em = null;

        try {
            tx.begin();
            em = createEntityManager();
            CategoryEntity entity = em.find(CategoryEntity.class, id);
            em.remove(entity);
            tx.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
            Try.run(tx::rollback);
            return Boolean.TRUE;

        } finally {
            if (nonNull(em))
                em.close();
        }

        return Boolean.TRUE;
    }

}