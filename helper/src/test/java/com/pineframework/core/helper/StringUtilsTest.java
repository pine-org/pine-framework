package com.pineframework.core.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void joinMethod_SeparatorIsNull_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> StringUtils.join(null, new String[]{}));
    }

    @Test
    void joinMethod_StringsAreNull_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> StringUtils.join("", null));
    }

    @Test
    void joinMethod_StringsAre_A_B_C_AndSeparatorIsEmpty_ReturnABCString() {
        String string = StringUtils.join("", "A", "B", "C");
        assertEquals("ABC", string);
    }

    @Test
    void joinMethod_StringsAreEmptyAndSeparatorIsNotEmpty_ReturnEmptyString() {
        String string = StringUtils.join(",", new String[]{});
        assertEquals("", string);
    }

    @Test
    void joinMethod_StringsAndSeparatorAreEmpty_ReturnEmptyString() {
        String string = StringUtils.join("", new String[]{});
        assertEquals("", string);
    }

    @Test
    void isEmptyMethod_NullStringAsParam_ReturnTrue() {
        boolean isEmpty = StringUtils.isEmpty(null);
        assertTrue(isEmpty);
    }

    @Test
    void isEmptyMethod_EmptyStringAsParam_ReturnTrue() {
        boolean isEmpty = StringUtils.isEmpty("");
        assertTrue(isEmpty);
    }

    @Test
    void isEmptyMethod_NotEmptyStringAsParam_ReturnFalse() {
        boolean isEmpty = StringUtils.isEmpty("value");
        assertFalse(isEmpty);
    }
}