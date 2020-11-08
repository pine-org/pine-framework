package com.pineframework.core.mongodb;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

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

}