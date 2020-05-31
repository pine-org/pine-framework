package com.pineframework.core.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Samana Alishirishahrbabak
 */
public final class GenericUtils {

    private GenericUtils() {
    }

    public static Type extract(Class<?> c, int index) {
        Type[] generics = ParameterizedType.class.cast(c.getGenericSuperclass()).getActualTypeArguments();
        return generics[index];
    }

}
