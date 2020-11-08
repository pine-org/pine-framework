package com.pineframework.core.business.helper;

import com.pineframework.core.datamodel.exception.RequiredException;

import static java.util.Objects.isNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class ObjectUtils {

    /**
     * If the object is null then throw {@link RequiredException}.
     *
     * @param o    object
     * @param name object name
     */
    public static void requiredNonNull(Object o, String name) {
        if (isNull(o))
            throw new RequiredException(name);
    }
}
