package com.pineframework.core.contract.service;

import java.io.Serializable;

public interface IdGenerator<T extends Serializable> {

    T next();
}
