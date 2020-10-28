package com.pineframework.core.business.helper;

import com.pineframework.core.business.exception.RequiredException;

import static java.util.Objects.isNull;

public class ObjectUtils {

    public static void requiredNonNull(Object o, String name) {
        if (isNull(o))
            throw new RequiredException(name);
    }
}
