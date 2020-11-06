package com.pineframework.core.contract.service.taskservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.MultiType;

/**
 * Business logic layer provide operations in order to manipulate at least two different transient type.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TaskService extends MultiType {

    /**
     * @param name register name
     * @param <T>  repository type
     * @return repository
     */
    @SuppressWarnings("unchecked")
    default <T extends Repository> T getRepository(String name) {
        return (T) getTypes().lookup(name);
    }
}
