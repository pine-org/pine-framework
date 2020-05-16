package com.pineframework.core.contract.transformer;

import com.pineframework.core.helper.ObjectRegistry;

public interface MultiTransformer {

    /**
     * When there are multi transformer for a specific type, it must be add them to a map.
     *
     * @return collections of transformer as a map
     */
    void initTransformerRegistry();

    ObjectRegistry getTransformerRegistry();

    <T extends Transformer> T getTransformer();

    default <T extends Transformer> T getTransformer(String name) {
        return (T) getTransformerRegistry().lookup(name);
    }
}
