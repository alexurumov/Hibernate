package getVillainsNames;

import java.sql.*;

import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root1234");

        final String connectionURL = "jdbc:mysql://localhost:3306/minions_db";

        Connection connection = DriverManager.getConnection(connectionURL, properties);



        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    v.name,\n" +
                "    COUNT(mv.minion_id) AS 'minions_count'\n" +
                "FROM villains v\n" +
                "RIGHT JOIN minions_villains mv\n" +
                "    ON v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING minions_count > 15\n" +
                "ORDER BY minions_count DESC;");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %s%n",
                    rs.getString("name"),
                    rs.getString("minions_count"));
        }

        connection.close();

    }
}
