package dataAccessLayer.dao;

import dataAccessLayer.connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Abstract class for the DB queries
 *
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    /**
     * The Class type T
     */
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(Class<T> type) {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates the Select query with selected field from as parameter
     *
     * @param field the parameter to be filtered
     * @return the created Query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").
                append(type.getSimpleName()).
                append(" WHERE ").
                append(field).
                append(" =?");
        return sb.toString();
    }

    /**
     * Creates the Insert query
     *
     * @param fields all the fields from the Class type T
     * @return the created Query
     */
    private String createInsertQuery(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").
                append(type.getSimpleName()).
                append(" (");
        for (Field field : fields) {
            sb.append(field.getName()).
                    append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(") VALUES (");
        for (Field field : fields) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        return sb.toString();
    }

    /**
     * Creates the Edit query
     *
     * @return the created Query
     */
    private String createEditQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").
                append(type.getSimpleName()).
                append(" SET ");
        for (Field field : type.getDeclaredFields()) {
            sb.append(field.getName()).
                    append(" = ").
                    append("?,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Finds the Object T by its id
     *
     * @param id the id of the searched object T
     * @return the found object T or null
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + sqlException.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Finds all the Objects T in the DB
     *
     * @return List of all Objects T in the DB
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + sqlException.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Inserts an Object T into the DB
     *
     * @param t the Object to be inserted
     * @return the id of the newly inserted Object
     */
    public int insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = -1;
        String query = createInsertQuery(type.getDeclaredFields());
        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            setPreparedStatementWithFields(t, statement);

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedId = resultSet.getInt(1);
            }

        } catch (SQLException | IllegalAccessException sqlException) {
            LOGGER.log(Level.WARNING, "DAO:insert " + sqlException.getMessage());
            return -1;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return insertedId;
    }

    /**
     * Edits an Object into the DB
     *
     * @param t the Object to be edited
     * @return the edited Object
     */
    public T edit(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createEditQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = setPreparedStatementWithFields(t, statement);

            statement.setInt(fields.length + 1, (Integer) fields[0].get(t));
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:edit " + sqlException.getMessage());
            return null;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Deletes an object from the DB
     *
     * @param t the Object to be deleted
     * @return the deleted Object
     */
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field field = type.getDeclaredFields()[0];
            field.setAccessible(true);
            statement.setInt(1, (Integer) field.get(t));
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + sqlException.getMessage());
            return null;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Sets the PreparedStatement with the fields from the Class Type T
     *
     * @param t         the Object with the fields
     * @param statement the statement to be executed
     * @return the Fields of the Object T
     */
    private Field[] setPreparedStatementWithFields(T t, PreparedStatement statement) throws IllegalAccessException, SQLException {
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object object = fields[i].get(t);
            if (object instanceof String) {
                statement.setString(i + 1, (String) object);
            } else if (object instanceof Integer) {
                statement.setInt(i + 1, (Integer) object);
            }
        }
        return fields;
    }

    /**
     * Function that creates a List of Objects of Class type T from a ResultSet
     *
     * @param resultSet the ResultSet from which the Objects will be extracted
     * @return the List of Objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
}
