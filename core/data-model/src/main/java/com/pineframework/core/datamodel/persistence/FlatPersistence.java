package com.pineframework.core.datamodel.persistence;

import com.pineframework.core.datamodel.filter.Filter;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @param <I> id
 * @author Saman Alishirishahrbabak
 */
public abstract class FlatPersistence<I extends Serializable> implements Persistable<I> {

    public static final int ALLOCATION_SIZE = 1;

    protected I id;

    protected LocalDateTime insertDate;

    protected Long insertUserId;

    protected Long insertUnitId;

    protected LocalDateTime modifyDate;

    protected Long modifyUserId;

    protected Long modifyUnitId;

    protected Integer version;

    /**
     * @return id
     */
    public I getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(I id) {
        this.id = id;
    }

    /**
     * @return date
     */
    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    /**
     * @param insertDate
     */
    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    /**
     * @return user id
     */
    public Long getInsertUserId() {
        return insertUserId;
    }

    /**
     * @param insertUserId
     */
    public void setInsertUserId(Long insertUserId) {
        this.insertUserId = insertUserId;
    }

    /**
     * @return organization id
     */
    public Long getInsertUnitId() {
        return insertUnitId;
    }

    /**
     * @param insertUnitId
     */
    public void setInsertUnitId(Long insertUnitId) {
        this.insertUnitId = insertUnitId;
    }

    /**
     * @return date
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return user id
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * @param modifyUserId
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * @return organization id
     */
    public Long getModifyUnitId() {
        return modifyUnitId;
    }

    /**
     * @param modifyUnitId
     */
    public void setModifyUnitId(Long modifyUnitId) {
        this.modifyUnitId = modifyUnitId;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Transient
    public <E extends FlatPersistence> Filter<E>[] toFilter() {
        return new Filter[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatPersistence)) return false;
        FlatPersistence<?> that = (FlatPersistence<?>) o;
        return Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
