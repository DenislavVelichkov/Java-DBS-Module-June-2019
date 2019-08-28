package ORM_Fundamentals_5.orm;

import ORM_Fundamentals_5.orm.annotations.PrimaryKey;
import orm.annotations.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_STATEMENT = "INSERT INTO `%s` (%s) VALUES (%s)";
    private static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_ALL_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s";
//    private static final String SELECT_FIRST_WITH_WHERE = SELECT_ALL_WITH_WHERE + " LIMIT 1";

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idField = getId(entity.getClass());
        idField.setAccessible(true);
        Object idValue = idField.get(entity);

        if (!checkIfTableExists(entity)) {
            this.doCreate(entity);
        }

        if (idValue == null || (int) idValue <= 0) {
            return doInsert(entity, idField);
        }

        return doUpdate(entity, idField);
    }

    public Iterable<E> find(Class<E> table) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return this.find(table, null);
    }

    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(table);

        Statement statement = connection.createStatement();
        String query = String.format(SELECT_ALL_WITH_WHERE,
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

    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        return find(table, where == null ? " 1 LIMIT 1" : where + " LIMIT 1").iterator().next();
    }

    private E mapResultToEntity(ResultSet rs, Class<E> table) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        E entity = table.getDeclaredConstructor().newInstance();

        Arrays.stream(table.getDeclaredFields())
            .forEach(f -> {
                f.setAccessible(true);
                String name = f.getName();
                Object value = null;
                try {
                    String classFieldName = this.getDatabaseFieldName(name);
                    value = this.getFieldValueFromResultSet(rs, classFieldName, f.getType());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    f.set(entity, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

        return entity;
    }

    private Object getFieldValueFromResultSet(ResultSet rs, String name, Class type) throws SQLException {
        Object result = null;

        if (type == int.class) {
            result = rs.getInt(name);
        } else if (type == String.class) {
            result = rs.getString(name);
        } else if (type == Date.class) {
            result = rs.getDate(name);
        }

        return result;
    }


    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                   .filter(f -> f.isAnnotationPresent(PrimaryKey.class))
                   .findFirst()
                   .orElseThrow(() ->
                                    new UnsupportedOperationException(
                                        "Entity does not have primary key")
                   );
    }

    private boolean doInsert(E entity, Field idField) throws SQLException {
        String tableName = this.getTableName(entity.getClass());
        String[] tableFields = this.getTableFields(entity);
        String[] tableValues = getTableValues(entity);

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
        String[] tableValues = getTableValues(entity);

        String[] fieldValuePairs = IntStream.range(0, tableFields.length)
                                       .mapToObj(i -> "`" + tableFields[i] + "`=" + tableValues[i])
                                       .toArray(String[]::new);

        String whereId = "`" + idField.getName() + "`='" + idField.get(entity) + "'";

        String query = String.format(UPDATE_STATEMENT,
            tableName,
            String.join(", ", fieldValuePairs),
            whereId);

        return this.connection.prepareStatement(query).execute();
    }

    private <E> void doCreate(E entity) throws SQLException {
        Field primaryKeyField = this.getPrimaryKeyField(entity);
        String primaryKeyColumnName = primaryKeyField.getAnnotation(PrimaryKey.class).name();
        String primaryKeyColumnType = this.getFieldType(primaryKeyField);
        String primaryKeyStatement = String.format("`%s` %s PRIMARY KEY AUTO_INCREMENT NOT NULL, ",
            primaryKeyColumnName, primaryKeyColumnType);

        String tableName = this.getTableName(entity.getClass());
        String columns = "";
        String query = String.format("CREATE TABLE %s (%s", tableName, columns);
        Field[] fields = entity.getClass().getDeclaredFields();


        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String fieldType = this.getFieldType(field);

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                columns += primaryKeyStatement;
                continue;
            }

            columns += String.format(
                "`%s` %s ",
                this.getClassFieldName(field.getName()),
                fieldType
            );

            if (i < fields.length - 1) {
                columns += ", ";
            }
        }

        query += columns + ");";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();
    }

    private String getFieldType(Field field) {
        Object fieldToCheck = field.getType();
        String result = "";

        if (fieldToCheck == int.class) {
            result = "INT";
        } else if (fieldToCheck == String.class) {
            result = "VARCHAR(50)";
        } else if (fieldToCheck == Date.class) {
            result = "DATE";
        }

        return result;
    }

    private String[] getTableValues(E entity) {
        return Arrays.stream(entity
                                 .getClass()
                                 .getDeclaredFields())
                   .filter(f -> f.isAnnotationPresent(Column.class))
                   .map(f -> {
                       f.setAccessible(true);
                       try {
                           Object value = f.get(entity);
                           return "'" + value.toString() + "'";

                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                           return "";
                       }
                   })
                   .toArray(String[]::new);
    }

    private String[] getTableFields(E entity) {
        return Arrays.stream(entity
                                 .getClass()
                                 .getDeclaredFields())
                   .filter(f -> f.isAnnotationPresent(Column.class))
                   .map(f -> {
                       f.setAccessible(true);
                       return f.getName();
                   })
                   .toArray(String[]::new);
    }

    // registration_date => registrationDate
    private String getClassFieldName(String name) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {
            char current = name.charAt(i);
            if (current == '_') { // 5
                current = name.charAt(i + 1); // 6
                current = (char) (current - ('a' - 'A'));
                i++;
            }

            result.append(current);
        }

        return result.toString();
    }

    // registrationDate => registration_date
    private String getDatabaseFieldName(String name) {
        return this.getClassFieldName(name.replaceAll("([A-Z])", "_$1").toLowerCase());
    }

    private String getTableName(Class aClass) {
        return aClass.getSimpleName().toLowerCase().concat("s");
    }

    private boolean checkIfTableExists(E entity) throws SQLException {
        String query = String.format(
            "SELECT TABLE_NAME FROM information_schema.TABLES WHERE " +
                "TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
            this.getTableName(entity.getClass()));

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        return rs.next();
    }

    private <E> Field getPrimaryKeyField(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                   .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                   .findFirst()
                   .orElseThrow(() -> new RuntimeException(
                       "Class " + entity.getClass().getSimpleName() + " does not have a primary key annotation"));
    }
}
