package com.pineframework.core.helper;

import com.pineframework.core.helper.generic.TypeResolver;
import io.vavr.control.Try;
import oracle.sql.TIMESTAMP;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.pineframework.core.helper.FileUtils.walkThrowPackage;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.substringBetween;

/**
 * @author Saman Alishirishahrbabak
 */
public final class ReflectionUtils {

    public static final int PUBLIC_FIELD = 25;

    private ReflectionUtils() {
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static List<Field> getAllFields(Class<?> c, Predicate<? super Field> filter) {
        List<Field> list = new ArrayList<>(filterFields(c, filter));

        while (nonNull(c.getSuperclass())) {
            c = c.getSuperclass();
            list.addAll(filterFields(c, filter));
        }

        return list;
    }

    public static List<Field> getAllFields(Class<?>[] clazz, Predicate<? super Field> filter) {
        List<Field> list = new ArrayList<>();

        for (Class<?> c : clazz) {
            list.addAll(filterFields(c, filter));
        }

        return list;
    }

    public static List<Field> filterFields(Class<?> c, Predicate<? super Field> predicate) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static Set<Class<?>> getClassesByAnnotation(String[] path, Class<? extends Annotation> annotation) {
        return Arrays.stream(path)
                .map(s -> getClassesByAnnotation(s, annotation))
                .reduce((root, child) -> {
                    root.addAll(child);
                    return root;
                })
                .orElse(new HashSet<>());
    }

    public static Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotation) {
        String root = packageName.split("\\.")[0];
        return walkThrowPackage(packageName)
                .filter(s -> s.endsWith(".class"))
                .map(s -> root + "." + substringBetween(s, root + ".", ".class"))
                .map(s -> Try.of(() -> Class.forName(s)).get())
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> getClasses(String packageName) {
        String root = packageName.split("\\.")[0];
        return walkThrowPackage(packageName)
                .filter(s -> s.endsWith(".class"))
                .map(s -> root + "." + substringBetween(s, root + ".", ".class"))
                .map(s -> Try.of(() -> Class.forName(s)).get())
                .collect(Collectors.toSet());
    }

    public static List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public static boolean contain(Class<?> c, String fieldName) {
        try {
            c.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException nsf) {
            return false;
        }
    }

    public static Field getField(Class<?> c, String fieldName) {
        while (!contain(c, fieldName) && nonNull(c.getSuperclass())) {
            c = c.getSuperclass();
        }

        try {
            return c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static <T extends Annotation> List<T> getAnnotationsOfFields(Class<?> c, Class<T> annotation) {
        return getAnnotatedFields(c, annotation).stream()
                .map(field -> field.getAnnotation(annotation))
                .collect(Collectors.toList());
    }

    public static Class<?> getFieldType(Class<?> clazz, Field field) {
        return field.getType() == Object.class
                ? TypeResolver.resolveRawClass(field.getGenericType(), clazz)
                : field.getType();
    }

    public static <T> T convertFromDatabaseType(Class<T> c, Object o) {
        return (T) DataBaseTypeConverter.converters.get(c).apply(o);
    }

    public static <T> T convertToPrimitive(Class<T> c, Object o) {
        return (T) PrimitiveConverter.converters.get(c).apply(o);
    }

    private static final class DataBaseTypeConverter {

        private static Map<Class, Function> converters = new HashMap();

        static {
            converters.putAll(PrimitiveConverter.converters);
            converters.put(LocalDate.class, (v) -> convertToLocalDate((TIMESTAMP) v));
            converters.put(LocalDateTime.class, (v) -> convertToLocalDateAndTime((TIMESTAMP) v));
            converters.put(LocalTime.class, (v) -> convertToLocalTime((TIMESTAMP) v));
        }

        private static Long convertToLocalTime(TIMESTAMP v) {
            return Try.of(() -> v.timestampValue().getTime()).get();
        }

        private static LocalDate convertToLocalDate(TIMESTAMP v) {
            return Try.of(() -> v.dateValue().toLocalDate()).get();
        }

        private static LocalDateTime convertToLocalDateAndTime(TIMESTAMP v) {
            return Try.of(() -> v.timestampValue().toLocalDateTime()).get();
        }
    }

    private static final class PrimitiveConverter {

        private static Map<Class, Function> converters = new HashMap();

        static {
            converters.put(Integer.class, (v) -> Integer.valueOf(String.valueOf(v)));
            converters.put(Long.class, (v) -> Long.valueOf(String.valueOf(v)));
            converters.put(String.class, (v) -> String.valueOf(v));
            converters.put(Boolean.class, (v) -> Integer.valueOf(String.valueOf(v)).intValue() == 1);
            converters.put(LocalDate.class, (v) -> extractDate(v));
            converters.put(LocalDateTime.class, (v) -> extractDateAndTime(v));
            converters.put(LocalTime.class, (v) -> extractTime(v));
        }

        private static LocalTime extractTime(Object v) {
            return LocalTime.parse(String.valueOf(v), ofPattern("HH:mm:ss"));
        }

        private static LocalDate extractDate(Object v) {
            return LocalDate.parse(String.valueOf(v), ofPattern("yyyy-MM-dd"));
        }

        private static LocalDateTime extractDateAndTime(Object v) {
            return LocalDateTime.parse(String.valueOf(v), ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
