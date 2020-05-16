package com.pineframework.core.contract.service.task;

import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.helper.ObjectRegistry;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TaskService extends BusinessService {

    void initRepositoryRegistry();

    ObjectRegistry getRepositoryRegistry();

    default <T extends FlatRepository> T getRepository(String name) {
        return (T) getRepositoryRegistry().lookup(name);
    }
}
