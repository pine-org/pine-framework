package com.pineframework.core.helper;

import com.pineframework.core.helper.generic.TypeResolver;
import io.vavr.control.Try;
import oracle.sql.TIMESTAMP;
import org.reflections.Reflections;

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

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;

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
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(annotation);
    }

    public static Set<Class<?>> getClasses(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getAllTypes()
                .stream()
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
        return (T) DataBaseTypeConverter.CONVERTERS.get(c).apply(o);
    }

    public static <T> T convertToPrimitive(Class<T> c, Object o) {
        return (T) PrimitiveConverter.CONVERTERS.get(c).apply(o);
    }

    private static final class DataBaseTypeConverter {

        private static final Map<Class, Function> CONVERTERS = new HashMap();

        static {
            CONVERTERS.putAll(PrimitiveConverter.CONVERTERS);
            CONVERTERS.put(LocalDate.class, (o) -> convertToLocalDate((TIMESTAMP) o));
            CONVERTERS.put(LocalDateTime.class, (o) -> convertToLocalDateAndTime((TIMESTAMP) o));
            CONVERTERS.put(LocalTime.class, (o) -> convertToLocalTime((TIMESTAMP) o));
        }

        private static Long convertToLocalTime(TIMESTAMP o) {
            return Try.of(() -> o.timestampValue().getTime()).get();
        }

        private static LocalDate convertToLocalDate(TIMESTAMP o) {
            return Try.of(() -> o.dateValue().toLocalDate()).get();
        }

        private static LocalDateTime convertToLocalDateAndTime(TIMESTAMP o) {
            return Try.of(() -> o.timestampValue().toLocalDateTime()).get();
        }
    }

    private static final class PrimitiveConverter {

        private static final Map<Class, Function> CONVERTERS = new HashMap();

        static {
            CONVERTERS.put(Integer.class, (o) -> Integer.valueOf(String.valueOf(o)));
            CONVERTERS.put(Long.class, (o) -> Long.valueOf(String.valueOf(o)));
            CONVERTERS.put(String.class, (o) -> String.valueOf(o));
            CONVERTERS.put(Boolean.class, (o) -> Integer.valueOf(String.valueOf(o)).intValue() == 1);
            CONVERTERS.put(LocalDate.class, (o) -> extractDate(o));
            CONVERTERS.put(LocalDateTime.class, (o) -> extractDateAndTime(o));
            CONVERTERS.put(LocalTime.class, (o) -> extractTime(o));
        }

        private static LocalTime extractTime(Object o) {
            return LocalTime.parse(String.valueOf(o), ofPattern("HH:mm:ss"));
        }

        private static LocalDate extractDate(Object o) {
            return LocalDate.parse(String.valueOf(o), ofPattern("yyyy-MM-dd"));
        }

        private static LocalDateTime extractDateAndTime(Object o) {
            return LocalDateTime.parse(String.valueOf(o), ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
