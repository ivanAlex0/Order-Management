package dataAccessLayer.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Singleton ConnectionFactory class to get the Connection to the DB
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     * The DB DRIVER
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //// useSSL=false due to a mySQL bug -> ;closing inbound before receiving peer's close_notify; -> https://bugs.mysql.com/bug.php?id=93590
    /**
     * The DB URL
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb?useSSL=false";
    /**
     * The DB USER
     */
    private static final String USER = "username";
    /**
     * The DB PASSWORD
     */
    private static final String PASS = "password";

    /**
     * The singleton instance
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    private static Connection connection;

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the connection to the DB
     *
     * @return the newly created connection or null
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to create the Connection to the DB");
            sqlException.printStackTrace();
        }
        return connection;
    }

    /**
     * Gets the connection to the DB after creating it
     *
     * @return the connection to the DB
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the Connection to the DB
     *
     * @param connection the Connection to be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the Connection to the DB");
            }
        }
    }

    /**
     * Closes a Statement
     *
     * @param statement the Statement to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close a Statement");
            }
        }
    }

    /**
     * Closes a ResultSet
     *
     * @param resultSet the ResultSet to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close a ResultSet");
            }
        }
    }
}
