package com.pineframework.core.helper.test;

import com.pineframework.core.helper.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void joinMethod_getNullPointerExceptionWhenSeparatorIsNull() {
        assertThrows(NullPointerException.class, () -> StringUtils.join(null, new String[]{}));
    }

    @Test
    void joinMethod_getNullPointerExceptionWhenStringsAreNull() {
        assertThrows(NullPointerException.class, () -> StringUtils.join("", null));
    }

    @Test
    void joinMethod_whenStringsAre_A_B_C_WithEmptySeparator_thenOutputIsABC() {
        String string = StringUtils.join("", "A", "B", "C");
        assertEquals("ABC", string);
    }

    @Test
    void joinMethod_whenStringsAreEmptyAndSeparatorIsNotEmpty_thenOutputIsEmptyString() {
        String string = StringUtils.join(",", new String[]{});
        assertEquals("", string);
    }

    @Test
    void joinMethod_whenStringsAndSeparatorAreEmpty_thenOutputIsEmptyString() {
        String string = StringUtils.join("", new String[]{});
        assertEquals("", string);
    }

    @Test
    void isEmptyMethod_whenInputIsNull_thenOutputIsTrue() {
        boolean isEmpty = StringUtils.isEmpty(null);
        assertTrue(isEmpty);
    }

    @Test
    void isEmptyMethod_whenInputIsEmpty_thenOutputIsTrue() {
        boolean isEmpty = StringUtils.isEmpty("");
        assertTrue(isEmpty);
    }

    @Test
    void isEmptyMethod_whenInputIsNotEmpty_thenOutputIsFalse() {
        boolean isEmpty = StringUtils.isEmpty("value");
        assertFalse(isEmpty);
    }
}