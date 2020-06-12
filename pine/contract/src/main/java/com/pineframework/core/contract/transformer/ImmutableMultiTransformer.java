package com.pineframework.core.contract.transformer;

import com.pineframework.core.helper.ObjectRegistry;

public interface ImmutableMultiTransformer {

    /**
     * When there are multi transformer for a specific type, it must be add them to a map.
     *
     * @return collections of transformer as a map
     */
    void initTransformerRegistry();

    ObjectRegistry getTransformerRegistry();

    <T extends ImmutableFlatTransformer> T getTransformer();

    default <T extends ImmutableFlatTransformer> T getTransformer(String name) {
        return (T) getTransformerRegistry().lookup(name);
    }
}
