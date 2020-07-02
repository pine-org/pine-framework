package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * @param <I> <description>id</description>
 * @param <E> <description>entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BatchRepository<I extends Serializable, E extends FlatPersistence<I>> extends FlatRepository<I, E> {

}
