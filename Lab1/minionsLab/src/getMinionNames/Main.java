package getMinionNames;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner in = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root1234");

        final String connectionURL = "jdbc:mysql://localhost:3306/minions_db";

        Connection connection = DriverManager.getConnection(connectionURL, properties);

        PreparedStatement stmt1 = connection.prepareStatement("SELECT name FROM villains WHERE id = ?;");

        int villain_id = Integer.parseInt(in.nextLine());

        stmt1.setInt(1, villain_id);

        ResultSet rs1 = stmt1.executeQuery();

        if (rs1.next()) {
            System.out.println("Villain: " + rs1.getString("name"));
        } else {
            System.out.println("No villain with ID " + villain_id + " exists in the database.");
            return;
        }

        PreparedStatement stmt2 = connection.prepareStatement("SELECT\n" +
                "    m.name,\n" +
                "    m.age\n" +
                "FROM minions m\n" +
                "RIGHT JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?;");

        stmt2.setInt(1, villain_id);

        ResultSet rs2 = stmt2.executeQuery();

        int minionIndex = 1;
        while (rs2.next()) {
            System.out.printf("%d. %s %d%n",
                    minionIndex++,
                    rs2.getString("name"),
                    rs2.getInt("age"));
        }
        connection.close();
    }
}

