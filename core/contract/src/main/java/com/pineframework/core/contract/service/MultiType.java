package com.pineframework.core.contract.service;

import com.pineframework.core.helper.ObjectRegistry;

/**
 * Provides a register to register and get different type.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface MultiType {
    /**
     * Initials {@link ObjectRegistry}
     */
    void initTypes();

    /**
     * Get registry to access all types.
     *
     * @return {@link ObjectRegistry}
     */
    @SuppressWarnings("rawtypes")
    ObjectRegistry getTypes();
}
