package com.pineframework.core.helper;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class EnumUtils {

    public static <T extends Enum> Optional<T> findEnumByPredicate(Class<T> type, Predicate<T> predicate) {
        return EnumSet.allOf(type).stream()
                .filter(predicate)
                .findFirst();
    }

    public static <T extends Enum> T getEnumByValueOfEnum(Class<T> type,
                                                          Function<T, Object> valueOfEnum,
                                                          Object otherValue) {
        return findEnumByPredicate(type, instance -> {
            Object value = valueOfEnum.apply(instance);
            return EnumFilters.FILTERS.get(value.getClass()).apply(value, otherValue);
        }).orElseThrow(() -> new RuntimeException(String.format("not found enum %s based on %s ",
                type.getSimpleName(),
                otherValue)));
    }

    private static class EnumFilters {
        private static final Map<Class, BiFunction<Object, Object, Boolean>> FILTERS = new HashMap();

        static {
            FILTERS.put(Integer.class, (v, otherValue) -> v.equals(Integer.valueOf(String.valueOf(otherValue))));
            FILTERS.put(Long.class, (v, otherValue) -> v.equals(Long.valueOf(String.valueOf(otherValue))));
            FILTERS.put(String.class, (v, otherValue) -> v.equals(String.valueOf(otherValue)));
        }
    }
}
