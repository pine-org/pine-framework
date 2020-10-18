package com.pineframework.core.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.slf4j.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pineframework.core.helper.StringUtils.isEmpty;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public final class JsonUtils {

    public static final Logger LOGGER = LogUtils.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static Map<String, String> convertToMap(String str) {
        return Try.of(() -> OBJECT_MAPPER.readValue(str, new TypeReference<Map<String, String>>() {
        })).get();
    }

    public static <T> T readArrayObject(String json, String array, Class<T> clazz) {
        return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(json).withArray(array), clazz)).get();
    }

    public static <T> T readArrayObject(File file, String array, Class<T> clazz) {
        return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(file).withArray(array), clazz)).get();
    }

    public static <T> T readObject(File file, String node, Class<T> clazz) {
        return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(file).with(node), clazz)).get();
    }

    /**
     * covert json object to specific java bean
     *
     * @param str
     * @param clazz
     * @param <T><description>class type</description>
     * @return <description>T</description>
     * @throws <description>IOException</description>
     */
    public static <T> T readObject(String str, Class<T> clazz) {
        return Try.of(() -> OBJECT_MAPPER.readValue(str, clazz)).get();
    }

    public static JsonObject readObject(File file) throws FileNotFoundException {
        JsonReader reader = Json.createReader(new FileInputStream(file));
        JsonObject object = reader.readObject();
        reader.close();
        return object;
    }

    public static <T> T readObject(String json, String node, Class<T> clazz) {
        return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(json).with(node), clazz)).get();
    }

    public static <T> T readPrimitiveObject(String json, String key, Class<T> c) {
        JsonNode jsonNode = Try.of(() -> OBJECT_MAPPER.readValue(json, JsonNode.class)).get();
        return ReflectionUtils.convertToPrimitive(c, jsonNode.get(key));
    }

    public static boolean contain(String json, String key) {
        if (isEmpty(json))
            return false;
        JsonNode jsonNode = Try.of(() -> OBJECT_MAPPER.readValue(json, JsonNode.class)).get();
        return jsonNode.has(key);
    }

    public static List<String> toStringList(JsonArray array) {
        return array.stream().map(val -> val.toString()).collect(Collectors.toList());
    }

    public static boolean isConvertible(String json, Class<?> c) {
        try {
            OBJECT_MAPPER.readValue(json, c);
            return true;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public static String asString(Object o) {
        return Try.of(() -> OBJECT_MAPPER.writeValueAsString(o)).get();
    }

    public static String asString(Object o, Class view) {
        return Try.of(() -> OBJECT_MAPPER.writerWithView(view).writeValueAsString(o)).get();
    }
}