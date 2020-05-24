package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.FlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
public interface EntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        T extends FlatTransformer<I, M, E>> extends BusinessService<I, M, E, R, T> {

}
