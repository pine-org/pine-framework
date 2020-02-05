package com.pineframework.core.helper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionUtilsTest {

    @Test
    void thereIsElementIn_NullArrayAsParam_ReturnFalse() {
        boolean thereIsElement = CollectionUtils.thereIsElementIn(null);
        assertFalse(thereIsElement);
    }

    @Test
    void thereIsElementIn_EmptyArrayAsParam_ReturnFalse() {
        boolean thereIsElement = CollectionUtils.thereIsElementIn(new Object[]{});
        assertFalse(thereIsElement);
    }

    @Test
    void thereIsElementIn_ArrayWithOneElementAsParam_ReturnTrue() {
        boolean thereIsElement = CollectionUtils.thereIsElementIn(new Object[]{new Object()});
        assertTrue(thereIsElement);
    }

    @Test
    void thereIsNoElementIn_NullArrayAsParam_ReturnTrue() {
        boolean hereIsNoElement = CollectionUtils.thereIsNoElementIn(null);
        assertTrue(hereIsNoElement);
    }

    @Test
    void thereIsNoElementIn_EmptyArrayAsParam_ReturnTrue() {
        boolean hereIsNoElement = CollectionUtils.thereIsNoElementIn(new Object[]{});
        assertTrue(hereIsNoElement);
    }

    @Test
    void thereIsNoElementIn_ArrayWithOneElementAsParam_ReturnFalse() {
        boolean hereIsNoElement = CollectionUtils.thereIsNoElementIn(new Object[]{new Object()});
        assertFalse(hereIsNoElement);
    }

    @Test
    void chooseRandomly_NullArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> CollectionUtils.chooseRandomly(null));
    }

    @Test
    void chooseRandomly_EmptyArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> CollectionUtils.chooseRandomly(new Object[]{}));
    }

    @Test
    void chooseRandomly_ArrayWithOneElementAsParam_ReturnTheSameElementAlways() {
        String theElement = CollectionUtils.chooseRandomly(new String[]{"element one"});
        assertEquals("element one", theElement);
    }

    @Test
    void chooseRandomly_ArrayWithTwoElementsAsParam_ReturnTheSameElementAlways() {
        String[] array = {"element one", "element two"};

        String theElement = CollectionUtils.chooseRandomly(array);
        Assertions.assertThat(theElement).isIn(array);
    }
}