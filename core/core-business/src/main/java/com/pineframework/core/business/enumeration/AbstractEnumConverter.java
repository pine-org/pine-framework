package com.pineframework.core.business.enumeration;

import com.pineframework.core.contract.enumeration.Convertible;
import com.pineframework.core.datamodel.exception.NotFoundEquivalentEnum;
import com.pineframework.core.helper.GenericUtils;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * All enum that use as field of persistable class muse use {@code AbstractEnumConverter}
 * for their converters to convert enum to database column and vice versa.
 *
 * @param <T> database column type
 * @param <E> implemented enum
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public abstract class AbstractEnumConverter<T, E extends Enum<E> & Convertible<T>> implements AttributeConverter<E, T> {

    private final Class<E> enumClassType;

    public AbstractEnumConverter() {
        enumClassType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    /**
     * enum to column
     *
     * @param attribute enum instance
     * @return value of enum
     */
    @Override
    public T convertToDatabaseColumn(E attribute) {
        return isNull(attribute) ? null : attribute.getValue();
    }

    /**
     * column  to enum
     *
     * @param dbData persist data
     * @return enum
     */
    @Override
    public E convertToEntityAttribute(T dbData) {
        if (isNull(dbData))
            return null;
        return EnumSet.allOf(enumClassType).stream()
                .filter(e -> Objects.equals(e.getValue(), dbData))
                .findFirst()
                .orElseThrow(NotFoundEquivalentEnum::new);
    }
}
