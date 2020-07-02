package com.pineframework.core.contract.service.taskservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.helper.ObjectRegistry;

public interface TaskService extends BusinessService {

    void initRepositoryRegistry();

    ObjectRegistry getRepositoryRegistry();

    default <T extends Repository> T getRepository(String name) {
        return (T) getRepositoryRegistry().lookup(name);
    }
}
