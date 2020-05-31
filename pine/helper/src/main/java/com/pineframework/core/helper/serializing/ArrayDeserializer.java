package com.pineframework.core.helper.serializing;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static com.pineframework.core.helper.ReflectionUtils.convertToPrimitive;
import static com.pineframework.core.helper.RegexUtils.isDateFormat;
import static com.pineframework.core.helper.RegexUtils.isDateTimeFormat;
import static com.pineframework.core.helper.RegexUtils.isTimeFormat;

public class ArrayDeserializer extends StdDeserializer<Object[]> {

    protected ArrayDeserializer() {
        super(Object[].class);
    }

    @Override
    public Object[] deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        return Arrays.stream(parser.readValueAs(Object[].class))
                .map(this::detectType)
                .toArray(Object[]::new);

    }

    private Object detectType(Object o) {
        Class<?> type = o.getClass();
        if (o.getClass().equals(String.class)) {
            if (isDateFormat(String.valueOf(o))) type = LocalDate.class;
            else if (isDateTimeFormat(String.valueOf(o))) type = LocalDateTime.class;
            else if (isTimeFormat(String.valueOf(o))) type = LocalTime.class;
        }
        return convertToPrimitive(type, o);
    }
}