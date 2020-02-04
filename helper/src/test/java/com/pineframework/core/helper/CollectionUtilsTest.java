package com.pineframework.core.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionUtilsTest {

    @Test
    void chooseRandomly_NullArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> CollectionUtils.chooseRandomly(null));
    }

    @Test
    void chooseRandomly_EmptyArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> CollectionUtils.chooseRandomly(new Object[]{}));
    }
}