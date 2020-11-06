package com.pineframework.core.contract.transformer;

import com.pineframework.core.contract.service.MultiType;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface ImmutableMultiTransformer extends MultiType {

    @SuppressWarnings({"rawtypes", "unchecked"})
    default <T extends ImmutableFlatTransformer> T getTransformer(String name) {
        return (T) getTypes().lookup(name);
    }
}
