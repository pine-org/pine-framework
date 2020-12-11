package com.pineframework.core.business.domain;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * All flat structure must be extended {@code AbstractPersistence}
 *
 * @param <I> id
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Access(AccessType.FIELD)
public abstract class AbstractAuditingEntity<I extends Serializable> extends FlatPersistence<I> {

    @Column(name = "INSERT_USER_ID", updatable = false)
    @Access(AccessType.PROPERTY)
    @Override
    public Long getInsertUserId() {
        return insertUserId;
    }

    @Column(name = "MODIFY_USER_ID")
    @Access(AccessType.PROPERTY)
    @Override
    public Long getModifyUserId() {
        return modifyUserId;
    }

    @Column(name = "INSERT_DATE", updatable = false)
    @Access(AccessType.PROPERTY)
    @Override
    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    @Column(name = "MODIFY_DATE")
    @Access(AccessType.PROPERTY)
    @Override
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    @Column(name = "INSERT_UNIT_ID", updatable = false)
    @Access(AccessType.PROPERTY)
    @Override
    public Long getInsertUnitId() {
        return super.getInsertUnitId();
    }

    @Column(name = "MODIFY_UNIT_ID")
    @Access(AccessType.PROPERTY)
    @Override
    public Long getModifyUnitId() {
        return super.getModifyUnitId();
    }

    @Version
    @Column(name = "VERSION")
    @Access(AccessType.PROPERTY)
    @Override
    public Integer getVersion() {
        return version;
    }

}
