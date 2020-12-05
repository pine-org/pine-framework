package com.pineframework.core.helper;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.pineframework.core.helper.CollectionUtils.chooseRandomly;
import static com.pineframework.core.helper.CollectionUtils.contains;
import static com.pineframework.core.helper.CollectionUtils.findAny;
import static com.pineframework.core.helper.CollectionUtils.findFrequency;
import static com.pineframework.core.helper.CollectionUtils.findRepetitiveElements;
import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.isThereAnyElement;
import static com.pineframework.core.helper.CollectionUtils.ofNullable;
import static com.pineframework.core.helper.CollectionUtils.subtract;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CollectionUtilsTest {

    @Test
    void isThereAnyElement_NullArrayAsParam_ReturnFalse() {
        isThereAnyElement((Object[]) null).ifTrue(() -> fail());
    }

    @Test
    void isThereAnyElement_EmptyArrayAsParam_ReturnFalse() {
        isThereAnyElement(new Object[]{}).ifTrue(() -> fail());
    }

    @Test
    void isThereAnyElement_ArrayWithOneElementAsParam_ReturnTrue() {
        isThereAnyElement(new Object[]{new Object()}).ifFalse(() -> fail());
    }

    @Test
    void chooseRandomly_NullArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> chooseRandomly(null));
    }

    @Test
    void chooseRandomly_EmptyArrayAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> chooseRandomly(new Object[]{}));
    }

    @Test
    void chooseRandomly_ArrayWithOneElementAsParam_ReturnTheSameElementAlways() {
        String theElement = chooseRandomly("element one");
        assertEquals("element one", theElement);
    }

    @Test
    void chooseRandomly_ArrayWithTwoElementsAsParam_ReturnTheSameElementAlways() {
        String[] array = {"element one", "element two"};

        String theElement = chooseRandomly(array);
        assertThat(theElement).isIn(array);
    }

    @Test
    void ofNullable_NullListAsParam_ReturnEmptyList() {
        List<Object> list = ofNullable((List<Object>) null);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    void ofNullable_NullSetAsParam_ReturnEmptyList() {
        Set<Object> set = ofNullable((Set<Object>) null);
        assertNotNull(set);
        assertEquals(0, set.size());
    }

    @Test
    void subtract_NullReferenceAsFirstAndSecondParam_IllegalArgumentExceptionThrown() {
        Collection<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThrows(IllegalArgumentException.class, () -> subtract(numbers, null));
    }

    @Test
    void subtract_NumberCollectionAsFirstParamAndNullReferenceAsSecondParam_IllegalArgumentExceptionThrown() {
        Collection<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThrows(IllegalArgumentException.class, () -> subtract(numbers, null));
    }

    @Test
    void subtract_NullReferenceAsFirstParamAndNumberCollectionAsSecondParam_IllegalArgumentExceptionThrown() {
        Collection<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThrows(IllegalArgumentException.class, () -> subtract(null, numbers));
    }

    @Test
    void subtract_DifferentNumberCollectionsAsFirstAndSecondParam_ReturnSubsetOfFirstParam() {
        Collection<Integer> numbers_1 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> numbers_2 = asList(6, 7, 8, 9);
        Collection<Integer> subtract = subtract(numbers_1, numbers_2);

        assertEquals(5, subtract.size());
        assertThat(subtract).containsOnly(1, 2, 3, 4, 5);
    }

    @Test
    void subtract_SameNumberCollectionsAsFirstAndSecondParam_ReturnEmptyCollection() {
        Collection<Integer> numbers_1 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> numbers_2 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> subtract = subtract(numbers_1, numbers_2);

        assertEquals(0, subtract.size());
    }

    @Test
    void intersection_NumberCollectionAsFirstParamAndNullReferenceAsSecondParam_IllegalArgumentExceptionThrown() {
        Collection<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThrows(IllegalArgumentException.class, () -> intersection(numbers, null));
    }

    @Test
    void intersection_NullReferenceAsFirstParamAndNumberCollectionAsSecondParam_IllegalArgumentExceptionThrown() {
        Collection<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThrows(IllegalArgumentException.class, () -> intersection(null, numbers));
    }

    @Test
    void intersection_DifferentNumberCollectionsAsFirstAndSecondParam_ReturnCommonElements() {
        Collection<Integer> numbers_1 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> numbers_2 = asList(1, 2, 3, 4, 5);
        Collection<Integer> commonElements = intersection(numbers_1, numbers_2);

        assertEquals(5, commonElements.size());
        assertThat(commonElements).containsOnly(1, 2, 3, 4, 5);
    }

    @Test
    void intersection_SameNumberCollectionsAsFirstAndSecondParam_ReturnTheSameCollection() {
        Collection<Integer> numbers_1 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> numbers_2 = asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> commonElements = intersection(numbers_1, numbers_2);

        assertEquals(9, commonElements.size());
        assertThat(commonElements).containsOnly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void findFrequency_NullCollectionAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findFrequency(null));
    }

    @Test
    void findFrequency_EmptyCollectionAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findFrequency(Collections.EMPTY_LIST));
    }

    @Test
    void findFrequency_NumberCollectionAsParam_ReturnRepeatedElements() {
        Collection<Integer> numbers = asList(1, 2, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 9, 9);
        Map<Integer, Long> frequencyTable = findFrequency(numbers);

        assertEquals(9, frequencyTable.size());
        assertEquals(1, frequencyTable.get(1));
        assertEquals(3, frequencyTable.get(2));
        assertEquals(1, frequencyTable.get(3));
        assertEquals(1, frequencyTable.get(4));
        assertEquals(1, frequencyTable.get(5));
        assertEquals(1, frequencyTable.get(6));
        assertEquals(4, frequencyTable.get(7));
        assertEquals(1, frequencyTable.get(8));
        assertEquals(2, frequencyTable.get(9));

    }

    @Test
    void findRepetitiveElements_NullCollectionAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findRepetitiveElements(null));
    }

    @Test
    void findRepetitiveElements_EmptyCollectionAsParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findRepetitiveElements(Collections.EMPTY_LIST));
    }

    @Test
    void findRepetitiveElements_NumberCollectionAsParam_ReturnRepeatedElements() {
        Collection<Integer> numbers = asList(1, 2, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 9, 9);
        Collection<Integer> repetitiveElements = findRepetitiveElements(numbers);

        assertEquals(3, repetitiveElements.size());
        assertThat(repetitiveElements).containsOnly(2, 7, 9);

    }

    @Test
    void contains_NullArrayAsFirstParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> contains(null, o -> true));
    }

    @Test
    void contains_EmptyArrayAsFirstParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> contains(new Object[]{}, o -> true));
    }

    @Test
    void contains_NumberArrayAsFirstParamAndNullConditionAsSecondParam_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> contains(new Object[]{new Object()}, null));
    }

    @Test
    void contains_NumberArrayAsFirstParamAndSpecificConditionAsSecondParam_ReturnTrue() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        contains(numbers, number -> number.equals(7)).ifFalse(() -> fail());
    }

    @Test
    void contains_NumberArrayAsFirstParamAndSpecificConditionAsSecondParam_ReturnFalse() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        contains(numbers, number -> number.equals(0)).ifTrue(() -> fail());
    }

    @Test
    void findAny_NullArrayAsFirstParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findAny(null, o -> true));
    }

    @Test
    void findAny_EmptyArrayAsFirstParam_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> findAny(new Object[]{}, o -> true));
    }

    @Test
    void findAny_NumberArrayAsFirstParamAndNullConditionAsSecondParam_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> findAny(new Object[]{new Object()}, null));
    }

    @Test
    void findAny_NumberArrayAsFirstParamAndSpecificConditionAsSecondParam_ReturnTheElement() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Optional<Integer> element = findAny(numbers, number -> number.equals(7));

        assertTrue(element.isPresent());
        assertEquals(7, element.get().intValue());
    }

    @Test
    void findAny_NumberArrayAsFirstParamAndSpecificConditionAsSecondParam_ReturnEmptyOptional() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Optional<Integer> element = findAny(numbers, number -> number.equals(0));

        assertFalse(element.isPresent());
    }

}