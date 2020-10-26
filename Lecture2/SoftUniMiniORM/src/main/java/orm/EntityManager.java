package orm;

import orm.annotations.Column;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_STATEMENT = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_FIRST_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s LIMIT 1";
    private static final String SELECT_ALL_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s";

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idField = this.getId(entity.getClass());
        idField.setAccessible(true);

        Object value = idField.get(entity);

        if (value == null || (int) value <= 0) {
            return this.doInsert(entity, idField);
        }

        return this.doUpdate(entity, idField);
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return this.find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(table);

        Statement statement = connection.createStatement();
        String query = String.format(
                SELECT_ALL_WITH_WHERE,
                tableName,
                where == null ? "" : "AND " + where);

        ResultSet rs = statement.executeQuery(query);

        List<E> result = new ArrayList<>();
        while (rs.next()) {
            E current = this.mapResultToEntity(rs, table);
            result.add(current);
        }

        return result;
    }

    @Override
    public E findFirst(Class<E> table) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        return find(table, where == null ? " LIMIT 1" : where + " LIMIT 1").iterator().next();
//        String tableName = this.getTableName(table);
//
//        Statement statement = connection.createStatement();
//        String query = String.format(
//                SELECT_FIRST_WITH_WHERE,
//                tableName,
//                where == null ? "" : "AND " + where);
//
//        ResultSet rs = statement.executeQuery(query);
//        rs.next();
//
//        return this.mapResultToEntity(rs, table);
    }

    private E mapResultToEntity(ResultSet rs, Class<E> table) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        E entity = table.getDeclaredConstructor().newInstance();

        Arrays.stream(table.getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = null;
                    try {
                        value = this.getValueFromResultSet(rs, name, field.getType());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    try {
                        field.set(entity, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return entity;
    }

    private Object getValueFromResultSet(ResultSet rs, String name, Class type) throws SQLException {
        Object result = null;

        if (type == int.class || type == Integer.class) {
            result = rs.getInt(name);
        } else if (type == String.class) {
            result = rs.getString(name);
        } else if (type == Long.class) {
            result = rs.getLong(name);
        } else if (type == Date.class) {
            result = rs.getDate(name);
        }

        return result;
    }

    private boolean doInsert(E entity, Field idField) throws SQLException {

        String tableName = this.getTableName(entity.getClass());
        String[] tableFields = this.getTableFields(entity);
        String[] tableValues = this.getTableValues(entity);

        String query = String.format(
                INSERT_STATEMENT,
                tableName,
                String.join(", ", tableFields),
                String.join(", ", tableValues));

        return this.connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field idField) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        String[] tableFields = this.getTableFields(entity);
        String[] tableValues = this.getTableValues(entity);

        String[] fieldValuePairs = IntStream.range(0, tableFields.length)
                .mapToObj(i -> "`" + tableFields[i] + "`=" + tableValues[i] + "")
                .toArray(String[]::new);

        String whereId = "`" + idField.getName() + "`='" + idField.get(entity) + "'";

        String query = String.format(
                UPDATE_STATEMENT,
                tableName,
                String.join(", ", fieldValuePairs),
                whereId);

        return this.connection.prepareStatement(query).execute();

    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() ->
                        new UnsupportedOperationException("Entity does not have primary key"));
    }

    private String getTableName(Class aClass) {
        return aClass.getSimpleName().toLowerCase().concat("s");
    }

    private String[] getTableFields(E entity) {
        return Arrays.stream(entity
                .getClass()
                .getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    return this.getNormalizedName(field.getName());
                })
                .toArray(String[]::new);
    }

    // registrationData -> registration_date
    private String getNormalizedName(String name) {
        return name.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    private String[] getTableValues(E entity) {
        return Arrays.stream(entity
                .getClass()
                .getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        return "'" + value.toString() + "'";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .toArray(String[]::new);
    }
}
