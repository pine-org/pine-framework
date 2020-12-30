package com.pineframework.core.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
    String column();

    String refColumn();

    String field();

    String[] ignore() default {};

    Class<?> relation();
}