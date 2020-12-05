package com.pineframework.core.contract.service;

import java.io.Serializable;

/**
 * Strategy to generate sequence of identities.
 *
 * @param <T>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface IdGenerator<T extends Serializable> {

    /**
     * Get the next identity.
     *
     * @return identity
     */
    T next();
}
