package com.pineframework.core.cache.oracle;

import com.pineframework.core.cache.annotation.CacheData;
import com.pineframework.core.cache.annotation.Enums;
import com.pineframework.core.cache.annotation.Mapped;
import com.pineframework.core.cache.annotation.NotCache;
import com.pineframework.core.cache.annotation.Relation;
import com.pineframework.core.cache.event.RowOperation;
import com.pineframework.core.cache.event.SimpleCacheObject;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.RowChangeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.pineframework.core.helper.CollectionUtils.concat;
import static com.pineframework.core.helper.CollectionUtils.isThereAnyElement;
import static com.pineframework.core.helper.CollectionUtils.mapOf;
import static com.pineframework.core.helper.CollectionUtils.mapTo;
import static com.pineframework.core.helper.DatabaseMetadataUtils.ROW_ID_INDEX;
import static com.pineframework.core.helper.DatabaseMetadataUtils.getColumns;
import static com.pineframework.core.helper.DatabaseMetadataUtils.registerChangeNotification;
import static com.pineframework.core.helper.EnumUtils.getEnumByValueOfEnum;
import static com.pineframework.core.helper.ReflectionUtils.PUBLIC_FIELD;
import static com.pineframework.core.helper.ReflectionUtils.convertFromDatabaseType;
import static com.pineframework.core.helper.ReflectionUtils.getAllFields;
import static com.pineframework.core.helper.ReflectionUtils.getAnnotatedFields;
import static com.pineframework.core.helper.ReflectionUtils.getAnnotationsOfFields;
import static com.pineframework.core.helper.ReflectionUtils.getClassesByAnnotation;
import static com.pineframework.core.helper.ReflectionUtils.getField;
import static com.pineframework.core.helper.ReflectionUtils.getFieldType;
import static com.pineframework.core.helper.StringUtils.convertToCamelCase;
import static com.pineframework.core.helper.StringUtils.convertToTitleCase;
import static org.apache.commons.lang3.reflect.MethodUtils.invokeMethod;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public abstract class OracleChangeDataCapture {

    private final Logger logger = LoggerFactory.getLogger(OracleChangeDataCapture.class);

    private OracleConnection connection;

    private String[] cacheObjectPackage;

    public void init() {
        getClassesByAnnotation(cacheObjectPackage, CacheData.class)
                .stream()
                .filter(c -> c.isAnnotationPresent(CacheData.class))
                .forEach(c -> {
                    registerChangeNotification(connection, event -> onChange(event, c), getTableName(c));
                    if (c.getAnnotation(CacheData.class).firstLoad())
                        firstLoad(c);
                });
    }

    private void firstLoad(Class<?> clazz) {
        try {
            String key = generateCacheKey(clazz);
            Map<String, String> mapping = createColumnFieldMapping(clazz, null);
            String sql = String.format("SELECT ROWID, %s.* FROM %s", getTableName(clazz), getTableName(clazz));

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next())
                        process(SimpleCacheObject.newBuilder()
                                .id(result.getString("ROWID"))
                                .key(key)
                                .operation(RowOperation.INSERT)
                                .data(createCacheRecord(clazz, result, mapping))
                                .build());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void onChange(DatabaseChangeEvent event, Class<?> clazz) {
        try {
            String key = generateCacheKey(clazz);
            Map<String, String> mapping = createColumnFieldMapping(clazz, null);
            RowChangeDescription[] changes = getTableChange(event);

            for (RowChangeDescription change : changes) {

                RowChangeDescription.RowOperation operationType = change.getRowOperation();
                String rowId = change.getRowid().stringValue();

                String sql = String.format("SELECT * FROM %s WHERE rowid=?", getTableName(clazz));

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(ROW_ID_INDEX, rowId);

                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next())
                            process(SimpleCacheObject.newBuilder()
                                    .id(rowId)
                                    .key(key)
                                    .operation(RowOperation.valueOf(operationType.name()))
                                    .data(createCacheRecord(clazz, result, mapping))
                                    .build());

                        if (operationType.equals(RowChangeDescription.RowOperation.DELETE))
                            process(SimpleCacheObject.newBuilder()
                                    .id(rowId)
                                    .key(key)
                                    .operation(RowOperation.valueOf(operationType.name()))
                                    .build());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Object createCacheRecord(Class<?> clazz, ResultSet result, Map<String, String> map) throws Exception {
        Object object = clazz.newInstance();

        for (Map.Entry<String, String> entry : map.entrySet())
            initialObject(object, entry.getValue(), result.getObject(entry.getKey()), result);

        return object;
    }

    private void initialObject(Object object, String fieldName, Object input, ResultSet result) throws Exception {

        Object value;

        Field field = getField(object.getClass(), fieldName);

        if (Objects.isNull(field)) {
            return;
        }

        if (field.isEnumConstant()) {
            value = getEnumByValueOfEnum((Class<? extends Enum>) field.getType(), item -> getValueOfEnum(item), input);
        } else if (field.isAnnotationPresent(Enums.class)) {
            value = getEnumByValueOfEnum(field.getAnnotation(Enums.class)
                    .enumClass(), item -> getValueOfEnum(item), input).name();
        } else if (field.isAnnotationPresent(Relation.class)) {
            Relation relation = field.getAnnotation(Relation.class);
            value = transformToRelation(relation, result.getObject(relation.column()));
        } else {
            value = convertFromDatabaseType(getFieldType(object.getClass(), field), input);
        }

        invokeMethod(object, convertToCamelCase("set", fieldName), value);
    }

    private Object transformToRelation(Relation relation, Object fk) throws Exception {

        Class<?> clazz = relation.relation();

        String sql = String.format("SELECT * FROM %s WHERE %s=?", getTableName(clazz), relation.refColumn());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, fk);

            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return createCacheRecord(clazz, result, createColumnFieldMapping(clazz, relation.ignore()));
            }
        }
    }

    private Map<String, String> createColumnFieldMapping(Class<?> clazz, String[] ignoreField) throws Exception {
        CacheData cacheData = clazz.getAnnotation(CacheData.class);

        List<String> fields = getClassFields(concat(cacheData.parent(), clazz));
        List<String> columns = getTableColumns(clazz, s -> fields.contains(convertToCamelCase(s.split("_"))));

        final Map<String, String> map = new HashMap<>();
        map.putAll(mapOf(columns, String::intern, s -> convertToCamelCase(s.split("_"))));
        map.putAll(mapOf(getAnnotationsOfFields(clazz, Mapped.class), Mapped::key, Mapped::value));
        map.putAll(mapOf(getAnnotationsOfFields(clazz, Relation.class), Relation::column, Relation::field));

        List<String> notCacheFields = mapTo(getAnnotatedFields(clazz, NotCache.class), field -> field.getName());
        notCacheFields.addAll(Arrays.asList(cacheData.ignore()));

        isThereAnyElement(ignoreField)
                .ifTrue(() -> notCacheFields.addAll(Arrays.asList(ignoreField)));

        return map.entrySet().stream()
                .filter(entry -> !notCacheFields.contains(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private RowChangeDescription[] getTableChange(DatabaseChangeEvent event) {
        return event.getTableChangeDescription()[0].getRowChangeDescription();
    }

    private List<String> getTableColumns(Class<?> clazz, Predicate<String> filter) throws SQLException {
        return getColumns(connection, getTableName(clazz), filter);
    }

    private List<String> getClassFields(Class<?>[] clazz) {
        return mapTo(getAllFields(clazz, field -> field.getModifiers() != PUBLIC_FIELD), field -> field.getName());
    }

    private String generateCacheKey(Class<?> clazz) {
        return convertToTitleCase(getTableName(clazz).split("_"));
    }

    protected String getTableName(Class<?> clazz) {
        CacheData cacheData = clazz.getAnnotation(CacheData.class);
        return String.format("%s.%s", cacheData.schema(), cacheData.tableName());
    }

    protected abstract void process(SimpleCacheObject object);

    protected abstract <T extends Enum> Object getValueOfEnum(T item);

}
