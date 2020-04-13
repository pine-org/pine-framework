package com.pineframework.core.helper;

import com.pineframework.core.datastructure.model.optional.BooleanOptional;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class CollectionUtils {

    public static final int DEFAULT_SIZE = 10;

    public static final int MIN_SIZE = 0;

    public static final List EMPTY_LIST = createSearchableList(Object.class, MIN_SIZE);

    private CollectionUtils() {
    }

    public static <E> List<E> createSearchableList(Class<E> c, int length) {

        if (length < MIN_SIZE) {
            throw new NullPointerException("array.exception.null");
        }

        return new ArrayList<E>(length);
    }

    public static <E> List<E> createEditableList(Class<E> c, int length) {
        if (length < MIN_SIZE) {
            throw new NullPointerException("array.exception.null");
        }

        return new LinkedList<E>();
    }

    public static <T> boolean isEmpty(T[] a) {
        return a == null && a.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> c) {
        return c == null && c.size() == 0;
    }

    public static <T, E> List<E> map(List<T> list, Function<T, E> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    public static <T, E> E[] map(T[] models, Function<T, E> function, Class<?> type) {
        return Arrays.stream(models)
                .map(function)
                .toArray(size -> (E[]) Array.newInstance(type, size));
    }

    public static <E> E[] createArray(Class<?> type, int length) {
        return (E[]) Array.newInstance(type, length);
    }

    /**
     * Return {@code true} if provided reference has at least one element otherwise
     * return {@code false}.
     *
     * @param array an array reference to be check for emptiness
     * @param <T>   the type of the reference
     * @return {@code true} if provided reference has at least one element otherwise
     * return {@code false}
     */
    public static <T> BooleanOptional isThereAnyElement(T[] array) {
        return BooleanOptional.check(array != null && array.length >= 1);
    }

    /**
     * Return {@code true} if provided reference has at least one element otherwise
     * return {@code false}.
     *
     * @param collection an collection reference to be check for emptiness
     * @param <T>        the type of the reference
     * @return {@code true} if provided reference has at least one element otherwise
     * return {@code false}
     */
    public static <T> BooleanOptional isThereAnyElement(Collection<T> collection) {
        return BooleanOptional.check(collection != null && !collection.isEmpty());
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param array the array reference to check for emptiness
     * @param <T>   the type of the reference
     * @throws IllegalArgumentException if {@code array} is {@code null} or is empty
     */
    public static <T> void requiredElement(T[] array) {
        isThereAnyElement(array)
                .ifFalse(() -> {
                    throw new IllegalArgumentException("array must have at least one element");
                });
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param collection the collection reference to check for emptiness
     * @param <T>        the type of the reference
     * @throws IllegalArgumentException if {@code collection} is {@code null} or is empty
     */
    public static <T> void requiredElement(Collection<T> collection) {
        isThereAnyElement(collection)
                .ifFalse(() -> {
                    throw new IllegalArgumentException("collection must have at least one element");
                });
    }

    /**
     * Return an element randomly and throws IllegalArgumentException if {@code array} is {@code null} or is empty.
     *
     * @param array the array reference for choosing an element randomly
     * @param <T>   the type of the reference
     * @return an element of array
     * @throws IllegalArgumentException if {@code array} is {@code null} or is empty
     */
    public static final <T> T chooseRandomly(T... array) {
        requiredElement(array);
        return array[(int) (Math.random() * array.length)];
    }

    /**
     * Checks {@code list} is null or no, if it is null then return empty list otherwise return the same list.
     * this method is design primarily for applying null functionality.
     *
     * @param list the list reference to be check nullity
     * @param <T>  the type of the reference
     * @return an empty list if {@code list} is null, otherwise return the same list.
     */
    public static <T> List<T> ofNullable(List<T> list) {
        return isNull(list) ? emptyList() : list;
    }

    /**
     * Checks {@code set} is null or no, if it is null then return empty set otherwise return the same set.
     * this method is design primarily for applying null functionality.
     *
     * @param set the set reference to be check nullity
     * @param <T> the type of the reference
     * @return an empty set if {@code set} is null, otherwise return the same set.
     */
    public static <T> Set<T> ofNullable(Set<T> set) {
        return isNull(set) ? emptySet() : set;
    }

    /**
     * Perform subtract operation between two collections on specific equality. All objects included in {@code c1} but
     * not included in {@code c2}
     *
     * @param c1  the first collection
     * @param c2  the second collection
     * @param poc the predicate of comparision
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection that, it is subset of {@code c1}
     */
    public static <E, T> Collection<E> subtract(Collection<E> c1, Collection<T> c2, BiFunction<E, T, Boolean> poc) {
        requiredElement(c1);
        requiredElement(c2);
        requireNonNull(poc);

        return c1.stream()
                .filter(e1 -> c2.stream().noneMatch(e2 -> poc.apply(e1, e2)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Perform subtract operation between two collections on object equality {@code e1.equals(e2)}. All objects
     * included in {@code c1} but not included in {@code c2}
     *
     * @param c1  the first collection
     * @param c2  the second collection
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection that, it is subset of {@code c1}
     */
    public static <E, T> Collection<E> subtract(Collection<E> c1, Collection<T> c2) {
        return subtract(c1, c2, (e1, e2) -> Objects.equals(e1, e2));
    }

    /**
     * Perform intersection operation between two collections then return common elements.
     *
     * @param c1  the first collection
     * @param c2  the second collection
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection include common items between {@code c1} and {@code c2}
     */
    public static <E, T> List<E> intersection(Collection<E> c1, Collection<T> c2, BiFunction<E, T, Boolean> condition) {
        requiredElement(c1);
        requiredElement(c2);
        requireNonNull(condition);

        return c1.stream()
                .filter(e1 -> c2.stream().anyMatch(e2 -> condition.apply(e1, e2)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Perform intersection operation between two collections on object equality {@code e1.equals(e2)} then return
     * common elements.
     *
     * @param c1  the first collection
     * @param c2  the second collection
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection include common elements between {@code c1} and {@code c2}
     */
    public static <E, T> List<E> intersection(Collection<E> c1, Collection<T> c2) {
        return intersection(c1, c2, (f, s) -> Objects.equals(f, s));
    }

    /**
     * Find frequency of the elements in a collection. Return a new map that include the elements as a key
     * and counting of them as a value.
     *
     * @param collection the reference to be check frequency of its elements
     * @param <E>        the type of elements
     * @return a new map that include the elements as a key
     * and counting of them as a value.
     */
    public static <E> Map<E, Long> findFrequency(Collection<E> collection) {
        requiredElement(collection);
        return collection.stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    /**
     * Find the elements that be repeated in  a collection then return as a new collection.
     *
     * @param collection the reference that to be finding repeated elements
     * @param <E>        the type of elements
     * @return new collection that include repeated elements
     */
    public static <E> Collection<E> findRepetitiveElements(Collection<E> collection) {
        requiredElement(collection);
        return collection.stream()
                .filter(i -> Collections.frequency(collection, i) > 1)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Returns {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     *
     * @param array     the reference to be check
     * @param condition the condition that array elements have to check via it
     * @param <T>       the type of elements
     * @return {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     */
    public static <T> BooleanOptional contains(T[] array, Predicate<? super T> condition) {
        requiredElement(array);
        requireNonNull(condition);

        return BooleanOptional.check(Arrays.stream(array).anyMatch(condition));
    }

    /**
     * Returns {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     *
     * @param array the reference to be check
     * @param item  the item that we want to find it
     * @param <T>   the type of elements
     * @return {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     */
    public static <T> boolean contains(T[] array, T item) {
        requiredElement(array);
        requireNonNull(item);

        return Arrays.stream(array).anyMatch(t -> t.equals(item));
    }

    /**
     * Find a element that is match via the {@code condition}. Return element that, is encapsulated to {@link Optional},
     * if there is no elements, return empty {@link Optional}
     *
     * @param array     the reference to be check
     * @param condition the condition that array elements have to check via it
     * @param <T>       the type of elements
     * @return element that, is encapsulated to {@link Optional}, if there is no elements, return empty {@link Optional}
     */
    public static <T> Optional<T> findAny(T[] array, Predicate<? super T> condition) {
        requiredElement(array);
        requireNonNull(condition);

        return Arrays.stream(array)
                .filter(condition)
                .findAny();
    }

    /**
     * Perform union operation on two arrays and return new array included elements of two the other arrays.
     *
     * @param array1 the first reference
     * @param array2 the second reference
     * @param <T>    the type of elements
     * @return new array included elements of two the other arrays
     */
    public static <T> T[] union(T[] array1, T... array2) {
        requiredElement(array1);
        requiredElement(array2);

        return ArrayUtils.addAll(array1, array2);
    }

    /**
     * Perform union operation on two collections and return new collection included elements
     * of two the other collections.
     *
     * @param c1  the first reference
     * @param c2  the second reference
     * @param <T> the type of elements
     * @param <E> the sub type of collection
     * @return new collection included elements of two the other collections
     */
    public static <T, E extends Collection<T>> E union(E c1, E c2) {
        requiredElement(c1);
        requiredElement(c2);

        c2.stream().forEach(c1::add);
        return c1;
    }

    /**
     * Create a map structure from collection elements.
     *
     * @param collection the reference of collection
     * @param key        the operation to create keys
     * @param value      the reference of value that will be map to key
     * @param <T>        the type of collection elements
     * @param <K>        the type of key
     * @param <V>        the type of value
     * @return new map of collection elements
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> collection, Function<T, K> key, Function<T, V> value) {
        return collection.stream().collect(Collectors.toMap(key, value));
    }

    /**
     * Create a map structure from array elements.
     *
     * @param a     the reference of array
     * @param key   the operation to create keys
     * @param value the reference of value that will be map to key
     * @param <T>   the type of collection elements
     * @param <K>   the type of key
     * @param <V>   the type of value
     * @return new map of array elements
     */
    public static <T, K, V> Map<K, V> toMap(T[] a, Function<T, K> key, Function<T, V> value) {
        return Arrays.stream(a).collect(Collectors.toMap(key, value));
    }

}