package com.pineframework.core.helper;

/**
 * @author Saman Alishirishahrbabak
 */
public final class CollectionUtils {

    private CollectionUtils() {
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
    public static <T> boolean thereIsElementIn(T[] array) {
        return array != null && array.length >= 1;
    }

    /**
     * Return {@code true} if provided reference does not have any element otherwise
     * return {@code false}.
     *
     * @param array an array reference to be check for emptiness
     * @param <T>   the type of the reference
     * @return {@code true} if provided reference does not have any element otherwise
     * return {@code false}
     */
    public static <T> boolean thereIsNoElementIn(T[] array) {
        return array == null || array.length == 0;
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
        if (thereIsNoElementIn(array)) {
            throw new IllegalArgumentException("array must have at least one element");
        }
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

}