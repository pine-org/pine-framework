package com.pineframework.core.helper;

import com.pineframework.core.helper.optional.BooleanOptional;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.frequency;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

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

    public static <T, E> List<E> mapTo(List<T> list, Function<T, E> f) {
        return list.stream().map(f).collect(Collectors.toList());
    }

    public static <T, E> Collection<E> mapTo(Collection<T> c, Function<T, E> f) {
        return c.stream().map(f).collect(toCollection(ArrayList::new));
    }

    public static <T, E> E[] mapTo(T[] models, Function<T, E> function, Class<E> type) {
        return Arrays.stream(models)
                .map(function)
                .toArray(size -> (E[]) Array.newInstance(type, size));
    }

    public static <T, E> void forEach(T[] models, Function<T, E> mapper, Consumer<E> endFunc) {
        Arrays.stream(models).map(mapper).forEach(endFunc);
    }

    public static <E> E[] createArray(Class<?> type, int length) {
        return (E[]) Array.newInstance(type, length);
    }

    /**
     * Return {@code true} if provided reference has at least one element otherwise
     * return {@code false}.
     *
     * @param a   an a reference to be check for emptiness
     * @param <T> the type of the reference
     * @return {@code true} if provided reference has at least one element otherwise
     * return {@code false}
     */
    public static <T> BooleanOptional isThereAnyElement(T[] a) {
        return BooleanOptional.of(a != null && a.length >= 1);
    }

    /**
     * Return {@code true} if provided reference has at least one element otherwise
     * return {@code false}.
     *
     * @param a      an a reference to be check for emptiness
     * @param <T>    the type of the reference
     * @param length expected the element number
     * @return {@code true} if provided reference has at least one element otherwise
     * return {@code false}
     */
    public static <T> BooleanOptional isThereAnyElement(T[] a, int length) {
        return BooleanOptional.of(a != null && a.length == length);
    }

    /**
     * Return {@code true} if provided reference has at least one element otherwise
     * return {@code false}.
     *
     * @param c   an c reference to be check for emptiness
     * @param <T> the type of the reference
     * @return {@code true} if provided reference has at least one element otherwise
     * return {@code false}
     */
    public static <T> BooleanOptional isThereAnyElement(Collection<T> c) {
        return BooleanOptional.of(c != null && !c.isEmpty());
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param a   the a reference to check for emptiness
     * @param <T> the type of the reference
     * @throws IllegalArgumentException if {@code a} is {@code null} or is empty
     */
    public static <T> void requiredElement(T[] a) {
        isThereAnyElement(a)
                .ifFalse(() -> {
                    throw new IllegalArgumentException("a must have at least one element");
                });
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param a      the a reference to check for emptiness
     * @param <T>    the type of the reference
     * @param length expected the element number
     * @throws IllegalArgumentException if {@code a} is {@code null} or is empty
     */
    public static <T> void requiredElement(T[] a, int length) {
        isThereAnyElement(a, length)
                .ifFalse(() -> {
                    throw new IllegalArgumentException(format("{@code a} must have exactly %d element", length));
                });
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param c   the c reference to check for emptiness
     * @param <T> the type of the reference
     * @throws IllegalArgumentException if {@code c} is {@code null} or is empty
     */
    public static <T> void requiredElement(Collection<T> c) {
        isThereAnyElement(c)
                .ifFalse(() -> {
                    throw new IllegalArgumentException("c must have at least one element");
                });
    }

    /**
     * Return an element randomly and throws IllegalArgumentException if {@code a} is {@code null} or is empty.
     *
     * @param a   the a reference for choosing an element randomly
     * @param <T> the type of the reference
     * @return an element of a
     * @throws IllegalArgumentException if {@code a} is {@code null} or is empty
     */
    public static final <T> T chooseRandomly(T... a) {
        requiredElement(a);
        return a[(int) (Math.random() * a.length)];
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

    public static <T> T[] ofNullable(T[] a) {
        return isNull(a) ? (T[]) Array.newInstance(Object.class, 0) : a;
    }

    /**
     * Perform subtract operation between two collections on specific equality. All objects included in {@code c1} but
     * not included in {@code c2}
     *
     * @param c1  the first collection
     * @param c2  the second collection
     * @param f   the comparison condition
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection that, it is subset of {@code c1}
     */
    public static <E, T> Collection<E> subtract(Collection<E> c1, Collection<T> c2, BiFunction<E, T, Boolean> f) {
        requiredElement(c1);
        requiredElement(c2);
        requireNonNull(f);

        return c1.stream()
                .filter(e1 -> c2.stream().noneMatch(e2 -> f.apply(e1, e2)))
                .collect(toCollection(ArrayList::new));
    }

    public static <E, T> E[] subtract(E[] c1, T[] c2, BiFunction<E, T, Boolean> f, Class<?> c) {
        Collection<E> collection = subtract(asList(c1), asList(c2), f);
        return collection.toArray((E[]) Array.newInstance(c, collection.size()));
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
     * @param f   comparison condition
     * @param <E> the type of object that held by the first collection
     * @param <T> the type of object that held by the second collection
     * @return new collection include common items between {@code c1} and {@code c2}
     */
    public static <E, T> Collection<E> intersection(Collection<E> c1, Collection<T> c2, BiFunction<E, T, Boolean> f) {
        requiredElement(c1);
        requiredElement(c2);
        requireNonNull(f);

        return c1.stream()
                .filter(e1 -> c2.stream().anyMatch(e2 -> f.apply(e1, e2)))
                .collect(toCollection(ArrayList::new));
    }

    public static <E, T> E[] intersection(E[] c1, T[] c2, BiFunction<E, T, Boolean> f, Class<?> c) {
        Collection<E> collection = intersection(asList(c1), asList(c2), f);
        return collection.toArray((E[]) Array.newInstance(c, collection.size()));

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
    public static <E, T> Collection<E> intersection(Collection<E> c1, Collection<T> c2) {
        return intersection(c1, c2, (first, second) -> Objects.equals(first, second));
    }

    /**
     * Find frequency of the elements in a c. Return a new map that include the elements as a key
     * and counting of them as a value.
     *
     * @param c   the reference to be check frequency of its elements
     * @param <E> the type of elements
     * @return a new map that include the elements as a key
     * and counting of them as a value.
     */
    public static <E> Map<E, Long> findFrequency(Collection<E> c) {
        requiredElement(c);
        return c.stream().collect(groupingBy(e -> e, counting()));
    }

    /**
     * Find the elements that be repeated in  a c then return as a new c.
     *
     * @param c   the reference that to be finding repeated elements
     * @param <E> the type of elements
     * @return new c that include repeated elements
     */
    public static <E> Collection<E> findRepetitiveElements(Collection<E> c) {
        requiredElement(c);
        return c.stream()
                .filter(e -> frequency(c, e) > 1)
                .collect(toCollection(HashSet::new));
    }

    /**
     * Returns {@code true} if this list contains the specified element that it is match with the {@code p}
     * otherwise return {@code false}.
     *
     * @param a   the reference to be check
     * @param p   the p that a elements have to check via it
     * @param <T> the type of elements
     * @return {@code true} if this list contains the specified element that it is match with the {@code p}
     * otherwise return {@code false}.
     */
    public static <T> BooleanOptional contains(T[] a, Predicate<? super T> p) {
        requiredElement(a);
        requireNonNull(p);

        return BooleanOptional.of(Arrays.stream(a).anyMatch(p));
    }

    /**
     * Returns {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     *
     * @param a    the reference to be check
     * @param item the item that we want to find it
     * @param <T>  the type of elements
     * @return {@code true} if this list contains the specified element that it is match with the {@code condition}
     * otherwise return {@code false}.
     */
    public static <T> boolean contains(T[] a, T item) {
        requiredElement(a);
        requireNonNull(item);

        return Arrays.stream(a).anyMatch(e -> e.equals(item));
    }

    /**
     * Find a element that is match via the {@code p}. Return element that, is encapsulated to {@link Optional},
     * if there is no elements, return empty {@link Optional}
     *
     * @param a   the reference to be check
     * @param p   the p that a elements have to check via it
     * @param <T> the type of elements
     * @return element that, is encapsulated to {@link Optional}, if there is no elements, return empty {@link Optional}
     */
    public static <T> Optional<T> findAny(T[] a, Predicate<? super T> p) {
        requiredElement(a);
        requireNonNull(p);

        return Arrays.stream(a)
                .filter(p)
                .findAny();
    }

    /**
     * Perform union operation on two arrays and return new array included elements of two the other arrays.
     *
     * @param a1  the first reference
     * @param a2  the second reference
     * @param <T> the type of elements
     * @return new array included elements of two the other arrays
     */
    public static <T> T[] union(T[] a1, T... a2) {
        requiredElement(a1);
        requiredElement(a2);

        return ArrayUtils.addAll(a1, a2);
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
     * Create a map structure from c elements.
     *
     * @param c     the reference of c
     * @param key   the operation to create keys
     * @param value the reference of value that will be map to key
     * @param <T>   the type of c elements
     * @param <K>   the type of key
     * @param <V>   the type of value
     * @return new map of c elements
     */
    public static <T, K, V> Map<K, V> mapOf(Collection<T> c, Function<T, K> key, Function<T, V> value) {
        return c.stream().collect(Collectors.toMap(key, value));
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
    public static <T, K, V> Map<K, V> mapOf(T[] a, Function<T, K> key, Function<T, V> value) {
        return Arrays.stream(a).collect(Collectors.toMap(key, value));
    }

    public static <E> List<E> listOf(Collection<E> c) {
        return new ArrayList<>(c);
    }

    public static <T> T[] concat(T[] t1, T... t2) {
        return ArrayUtils.addAll(t1, t2);
    }

    public static <T, E extends Collection<T>> E concat(E t1, E t2) {
        t2.stream().forEach(t1::add);
        return t1;
    }

}