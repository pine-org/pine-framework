package com.pineframework.core.business.service;

import com.pineframework.core.datastructure.persistence.FlatPersistence;

import java.io.Serializable;

public interface Repository<I extends Serializable, E extends FlatPersistence> {
}
