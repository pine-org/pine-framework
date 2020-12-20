package com.pineframework.core.helper;

import io.vavr.control.Try;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.rangeClosed;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public final class DatabaseMetadataUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMetadataUtils.class);

    public static final int ROW_ID_INDEX = 1;

    private DatabaseMetadataUtils() {
    }

    public static String[] getColumns(Connection c, String table) {
        return createColumns(c, getResultSetMetaData(c, table));
    }

    public static List<String> getColumns(Connection c, String table, Predicate<String> filter) {
        return createColumns(c, getResultSetMetaData(c, table), filter);
    }

    public static List<String> getColumns(Connection c, String table, Function<String, String> mapper) {
        return createColumns(c, getResultSetMetaData(c, table), mapper);
    }

    public static String[] createColumns(Connection c, ResultSetMetaData md) {
        return fetchColumnNames(md).toArray(String[]::new);
    }

    public static List<String> createColumns(Connection c, ResultSetMetaData metadata, Predicate<String> filter) {
        return fetchColumnNames(metadata).filter(filter).collect(Collectors.toList());
    }

    public static List<String> createColumns(Connection c, ResultSetMetaData md, Function<String, String> mapper) {
        return fetchColumnNames(md).map(mapper).collect(Collectors.toList());
    }

    public static List<String> getTables(Connection connection) throws SQLException {
        List<String> tables = new ArrayList<>();

        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet rs = metadata.getTables(null, null, null, new String[]{"TABLE"});
        while (rs.next())
            tables.add(rs.getString("TABLE_NAME"));

        rs.close();

        return tables;
    }

    public static Properties getListenerProperties() {
        Properties properties = new Properties();
        properties.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
        return properties;
    }

    public static void registerChangeNotification(OracleConnection c, DatabaseChangeListener listener, String table) {
        Try.run(() -> {
            DatabaseChangeRegistration notification = c.registerDatabaseChangeNotification(getListenerProperties());
            Statement statement = c.createStatement();
            notification.addListener(listener);
            ((OracleStatement) statement).setDatabaseChangeRegistration(notification);
            statement.executeQuery(String.format("select * from %s where 1=2", table));
            statement.close();
        });

    }

    private static ResultSetMetaData getResultSetMetaData(Connection c, String table) {
        try (PreparedStatement statement = c.prepareStatement("SELECT * FROM " + table)) {
            try (ResultSet result = statement.executeQuery()) {
                return result.getMetaData();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private static Stream<String> fetchColumnNames(ResultSetMetaData metadata) {
        return rangeClosed(1, getColumnCount(metadata)).mapToObj(i -> getColumnName(metadata, i));
    }

    private static String getColumnName(ResultSetMetaData metadata, int i) {
        return Try.of(() -> metadata.getColumnName(i)).get();
    }

    private static int getColumnCount(ResultSetMetaData metadata) {
        return Try.of(() -> metadata.getColumnCount()).get();
    }

}
