import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    private static final  String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db";
    public static void main(String[] args) throws SQLException {


        Properties prop = new Properties();

        prop.setProperty("user", "root");
        prop.setProperty("password", "1234");

        Connection connection = DriverManager.getConnection(CONNECTION_URL, prop);

        Engine engine = new Engine(connection);
        engine.run();


    }
}
