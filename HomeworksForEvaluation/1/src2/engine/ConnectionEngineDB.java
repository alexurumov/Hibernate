package engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionEngineDB {

    final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db";
    final static String USERNAME = "root";
    final static String PASSWORD = "root1234";

    public ConnectionEngineDB() {
    }

    public Connection connectDB() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        Connection connection = DriverManager.getConnection(CONNECTION_URL,properties);
        return connection;
    }
}
