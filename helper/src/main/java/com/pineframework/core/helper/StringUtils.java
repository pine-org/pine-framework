package com.pineframework.core.helper;

import java.util.Arrays;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Saman Alishirishahrbabak
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Returns {@code true} if the provided reference is {@code null} otherwise
     * returns {@code false}.
     *
     * @param str a string reference to be checked against {@code null}

     * @return {@code true} if the provided reference is {@code null} otherwise
     * {@code false}
     */
    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    /**
     * Returns {@code true} if the provided reference is {@code not null} otherwise
     * returns {@code false}.
     *
     * @param str a string reference to be checked against {@code  not null}

     * @return {@code true} if the provided reference is {@code  not null} otherwise
     * {@code false}
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Checks that the specified object reference is not {@code null} and not empty. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors.
     *
     * @param str the string reference to check for emptiness
     * @throws IllegalArgumentException if {@code str} is {@code null} or is empty
     */
    public static void requireNotEmpty(String str) {
        if (isEmpty(str)) {
            throw new IllegalArgumentException("string cannot be null or empty");
        }
    }

    /**
     * Returns a new String composed of copies of the
     * {@code CharSequence strings} joined together with a copy of
     * the specified {@code separator}.
     *
     * @param separator the separator that separates each element
     * @param strings   the strings to join together
     *
     * @return a new {@code String} that is composed of the {@code strings}
     * separated by the {@code separator}
     */
    public static String join(String separator, String... strings) {
        return String.join(separator, strings);
    }

    /**
     * Return new String as title case style word. This
     * method is designed primarily for apply on a word.
     *
     * @param word the {@code word} that be changed to title case word
     *
     * @return new String as title case style word
     */
    public static String convertToTitleCase(String word) {
        requireNotEmpty(word);
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }

    /**
     * Return new String that applied title case style on each words. This
     * method is designed primarily for apply on a sentences. The sentences have to split
     * then pass to {@code convertToTitleCase}.
     *
     * @param words the {@code words} that be changed to title case words
     *
     * @return new String as title case style sentence
     */
    public static String convertToTitleCase(String... words) {
        Objects.requireNonNull(words);
        return Stream.of(words)
                .map(StringUtils::convertToTitleCase)
                .collect(Collectors.joining());
    }

    /**
     * Return new String that applied camel case style on the phrases. This
     * method is designed primarily for apply on a phrases. the phrases have to split
     * then pass to {@code convertToCamelCase}.
     *
     * @param words the {@code words} that be changed to title case words
     *
     * @return new String as title case style sentence
     */
    public static String convertToCamelCase(String... words) {
        Objects.requireNonNull(words);
        Spliterator<String> sp = Arrays.spliterator(words);
        sp.tryAdvance(String::toLowerCase);
        return StreamSupport.stream(sp, false)
                .reduce((first, rest) -> first.concat(convertToTitleCase(rest)))
                .get();
    }
}
