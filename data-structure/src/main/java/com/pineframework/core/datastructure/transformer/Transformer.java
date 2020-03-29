package com.pineframework.core.datastructure.transformer;

import com.pineframework.core.datastructure.model.Transient;
import com.pineframework.core.datastructure.persistence.Persistence;

import java.io.Serializable;

public interface Transformer<I extends Serializable, M extends Transient, P extends Persistence> {
}
