package com.pineframework.core.business.domain;

import com.pineframework.core.business.domain.listener.AuditingEntityListener;
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
 * all tree structure must use {@code AbstractTreePersistence}
 *
 * @param <I> id
 * @param <E> entity
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Access(AccessType.PROPERTY)
public abstract class AbstractAuditingTreeEntity
        <I extends Serializable, E extends TreePersistence> extends TreePersistence<I, E> {

    @Override
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public E getParent() {
        return parent;
    }

    @Override
    public void setParent(E parent) {
        this.parent = parent;
    }

    @Override
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }

    @Override
    @Column(name = "PATH")
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    @Column(name = "INSERT_USER_ID", updatable = false)
    public Long getInsertUserId() {
        return insertUserId;
    }

    @Override
    public void setInsertUserId(Long insertUserId) {
        this.insertUserId = insertUserId;
    }

    @Column(name = "MODIFY_USER_ID")
    public Long getModifyUserId() {
        return modifyUserId;
    }

    @Override
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    @Override
    @Column(name = "INSERT_DATE", updatable = false)
    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    @Override
    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    @Column(name = "MODIFY_DATE")
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    @Column(name = "INSERT_UNIT_ID", updatable = false)
    public Long getInsertUnitId() {
        return super.getInsertUnitId();
    }

    @Override
    public void setInsertUnitId(Long insertUnitId) {
        super.setInsertUnitId(insertUnitId);
    }

    @Override
    @Column(name = "MODIFY_UNIT_ID")
    public Long getModifyUnitId() {
        return super.getModifyUnitId();
    }

    @Override
    public void setModifyUnitId(Long modifyUnitId) {
        super.setModifyUnitId(modifyUnitId);
    }

    @Override
    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
