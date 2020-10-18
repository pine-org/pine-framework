package com.pineframework.core.tutorial.eshop.testmongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class CategoryOgmRepository {

    private final Logger logger = LoggerFactory.getLogger(CategoryOgmRepository.class);

    @PersistenceContext
    private EntityManager em;

    public Long save(CategoryEntity category) {
        em.persist(category);
        return category.getId();
    }

}