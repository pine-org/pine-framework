package com.pineframework.core.datastructure.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.Serializable;
import java.util.Objects;

/**
 * All models such as value objects, data transfer object (DTO), etc should be extended from {@code AbstractTransient}.
 * {@code AbstractTransient} implements equals and hash code and also {@code toString} method.
 *
 * @param <I> the type of identity
 */
public abstract class AbstractTransient<I extends Serializable, V extends Serializable> implements Transient<I, V> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private I id;

    private V version;

    @Override
    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public V getVersion() {
        return version;
    }

    public void setVersion(V version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AbstractTransient)) {
            return false;
        }

        AbstractTransient<?, ?> that = (AbstractTransient<?, ?>) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return Try.of(() -> objectMapper.writeValueAsString(this)).get();
    }

}
