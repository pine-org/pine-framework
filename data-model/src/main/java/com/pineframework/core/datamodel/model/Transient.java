package com.pineframework.core.datamodel.model;

import java.io.Serializable;

/**
 * Use to transient models. Every transient models must have identity. It should be better implement models as a
 * immutable class.
 * Transient objects have to be serializable it means use {@link Serializable} and also they have got to
 * serialVersionUID.
 *
 * @param <I> the type of identity
 * @author Saman Alishirishahrbabak
 */
public interface Transient<I extends Serializable> extends Serializable {

    /**
     * Identity accessor.
     *
     * @return identity
     */
    I getId();

    /**
     * Version accessor.
     *
     * @return data version
     */
    Integer getVersion();
}
