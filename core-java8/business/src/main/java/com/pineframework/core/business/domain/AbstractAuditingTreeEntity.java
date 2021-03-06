package com.pineframework.core.business.domain;

import com.pineframework.core.datamodel.persistence.TreePersistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * All tree structure must use {@code AbstractTreePersistence}
 *
 * @param <I> id
 * @param <E> entity
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Access(AccessType.FIELD)
public abstract class AbstractAuditingTreeEntity<I extends Serializable, E extends TreePersistence<I, E>>
        extends TreePersistence<I, E> {

    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Access(AccessType.PROPERTY)
    @Override
    public E getParent() {
        return parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    @Override
    public List<E> getChildren() {
        return children;
    }

    @Column(name = "PATH")
    @Access(AccessType.PROPERTY)
    @Override
    public String getPath() {
        return path;
    }

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
