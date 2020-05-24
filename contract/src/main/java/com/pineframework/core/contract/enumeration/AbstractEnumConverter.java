package com.pineframework.core.contract.enumeration;

import com.pineframework.core.helper.GenericUtils;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.Objects;

/**
 * all enum that use as field of entity muse use {@code AbstractEnumConverter}
 * for their converters to convert enum to database column and vice versa.
 *
 * @param <T> database column type
 * @param <E> implemented enum
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public abstract class AbstractEnumConverter<T, E extends Enum<E> & Convertible<T>> implements AttributeConverter<E, T> {

    private Class<E> enumClassType;

    public AbstractEnumConverter() {
        enumClassType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    /**
     * enum to column
     *
     * @param attribute
     * @return value of enum
     */
    @Override
    public T convertToDatabaseColumn(E attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    /**
     * column  to enum
     *
     * @param dbData
     * @return enum
     */
    @Override
    public E convertToEntityAttribute(T dbData) {

        if (Objects.isNull(dbData)) return null;

        return EnumSet.allOf(enumClassType).stream()
            .filter(e -> Objects.equals(e.getValue(), dbData))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }
}
