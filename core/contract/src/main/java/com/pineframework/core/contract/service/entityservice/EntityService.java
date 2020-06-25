package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface EntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>,
        R extends Repository<I, E>
        > extends BusinessService<I, E, M, B, T, R> {

}
