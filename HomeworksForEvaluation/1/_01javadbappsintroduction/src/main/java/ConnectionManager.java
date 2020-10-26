import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private Properties properties;
    private Connection connection;
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db";
    public ConnectionManager(String user, String password){
        this.setProperties(user,password);
        try {
            this.connect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void setProperties(String user, String password) {
        this.properties = new Properties();
        this.properties.setProperty("user", user);
        this.properties.setProperty("password", password);
    }
    private void connect() throws SQLException {
        this.connection= DriverManager.getConnection(CONNECTION_URL, properties);
    }

    protected Connection getConnection() {
        return this.connection;
    }
}

