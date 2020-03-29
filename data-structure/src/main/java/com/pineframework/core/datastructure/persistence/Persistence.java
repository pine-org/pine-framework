package com.pineframework.core.datastructure.persistence;

import java.io.Serializable;

public interface Persistence<I extends Serializable, V extends Serializable> {

    I getId();

    V getVersion();
}
