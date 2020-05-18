package com.pineframework.core.contract.service;

import java.io.Serializable;

public interface IdentityGenerator<T extends Serializable> {

    T next();
}
