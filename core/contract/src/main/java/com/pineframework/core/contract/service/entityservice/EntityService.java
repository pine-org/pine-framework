package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.service.BusinessService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

public interface EntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends Repository<I, E>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>> extends BusinessService<I, M, E, R, B, T> {

}
