package com.pineframework.core.datamodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import io.vavr.control.Try;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * All models such as value objects, data transfer object (DTO), etc should be extended from {@code AbstractTransient}.
 * {@code FlatTransient} implements equals and hash code and also {@code toString} method.
 *
 * @param <I> the type of identity
 */
public abstract class FlatTransient<I extends Serializable> implements Transient<I> {

    protected final I id;

    protected final LocalDateTime insertDate;

    protected final Long insertUserId;

    protected final Long insertUnitId;

    protected final LocalDateTime modifyDate;

    protected final Long modifyUserId;

    protected final Long modifyUnitId;

    protected final Integer version;

    @JsonIgnore
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected FlatTransient(FlatTransient.Builder builder) {
        this.id = (I) builder.id;
        this.insertDate = builder.insertDate;
        this.insertUserId = builder.insertUserId;
        this.insertUnitId = builder.insertUnitId;
        this.modifyDate = builder.modifyDate;
        this.modifyUserId = builder.modifyUserId;
        this.modifyUnitId = builder.modifyUnitId;
        this.version = builder.version;
    }

    /**
     * @return id
     */
    public I getId() {
        return id;
    }

    /**
     * @return insert date
     */
    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    /**
     * @return user id
     */
    public Long getInsertUserId() {
        return insertUserId;
    }

    /**
     * @return organization id
     */
    public Long getInsertUnitId() {
        return insertUnitId;
    }

    /**
     * @return date
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * @return user id
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * @return organization id
     */
    public Long getModifyUnitId() {
        return modifyUnitId;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPersistence<?> that = (FlatPersistence<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersion());
    }

    @Override
    public String toString() {
        return Try.of(() -> objectMapper.writeValueAsString(this)).get();
    }

    public abstract static class Builder<I extends Serializable,
            M extends FlatTransient<I>,
            B extends FlatTransient.Builder<I, M, B>> {

        protected I id;

        protected LocalDateTime insertDate;

        protected Long insertUserId;

        protected Long insertUnitId;

        protected LocalDateTime modifyDate;

        protected Long modifyUserId;

        protected Long modifyUnitId;

        protected Integer version;

        protected Builder() {
        }

        public B id(I id) {
            this.id = id;
            return (B) this;
        }

        public B insertDate(LocalDateTime insertDate) {
            this.insertDate = insertDate;
            return (B) this;
        }

        public B insertUserId(Long insertUserId) {
            this.insertUserId = insertUserId;
            return (B) this;
        }

        public B insertUnitId(Long insertUnitId) {
            this.insertUnitId = insertUnitId;
            return (B) this;
        }

        public B modifyDate(LocalDateTime modifyDate) {
            this.modifyDate = modifyDate;
            return (B) this;
        }

        public B modifyUserId(Long modifyUserId) {
            this.modifyUserId = modifyUserId;
            return (B) this;
        }

        public B modifyUnitId(Long modifyUnitId) {
            this.modifyUnitId = modifyUnitId;
            return (B) this;
        }

        public B version(Integer version) {
            this.version = version;
            return (B) this;
        }

        public B from(M model) {
            this.id = model.getId();
            this.insertDate = model.getInsertDate();
            this.insertUserId = model.getInsertUserId();
            this.insertUnitId = model.getInsertUnitId();
            this.modifyDate = model.getModifyDate();
            this.modifyUserId = model.getModifyUserId();
            this.modifyUnitId = model.getModifyUnitId();
            this.version = model.getVersion();
            return (B) this;
        }

        public abstract M build();
    }

}
