package com.pineframework.core.contract.service.taskservice;

import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.helper.ObjectRegistry;

public interface TaskService extends BusinessService {

    void initRepositoryRegistry();

    ObjectRegistry getRepositoryRegistry();

    default <T extends JpaRepository> T getRepository(String name) {
        return (T) getRepositoryRegistry().lookup(name);
    }
}
