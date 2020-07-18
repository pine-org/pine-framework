package com.pineframework.core.business.domain;

import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.DateUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * insert and update audit info
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class AuditingEntityListener {

    /**
     * before insert records
     *
     * @param target
     */
    @PrePersist
    public void touchForCreate(Object target) {
        FlatPersistence entity = (FlatPersistence) target;
        entity.setInsertUserId(1L);
        entity.setInsertDate(DateUtils.getCurrentLocalDateTime());
        entity.setInsertUnitId(1L);
    }

    /**
     * before update records
     *
     * @param target
     */
    @PreUpdate
    public void touchForUpdate(Object target) {
        FlatPersistence entity = (FlatPersistence) target;
        entity.setModifyUserId(1L);
        entity.setModifyDate(DateUtils.getCurrentLocalDateTime());
        entity.setModifyUnitId(1L);
    }
}