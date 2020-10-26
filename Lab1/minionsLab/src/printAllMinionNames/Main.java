package printAllMinionNames;

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

        PreparedStatement getMinionsCount = connection.prepareStatement("SELECT\n" +
                "    COUNT(id)\n" +
                "FROM minions;");
        ResultSet minionsCount = getMinionsCount.executeQuery();
        minionsCount.next();
        int n = minionsCount.getInt(1);

        PreparedStatement printMinionName = connection.prepareStatement("SELECT name\n" +
                "FROM minions\n" +
                "WHERE id = ?;");

        int firstIndex = 1;
        int secondIndex = n;

        for (int i = 0; i < n / 2; i++) {

            printMinionName.setInt(1, firstIndex++);
            ResultSet firstMinion = printMinionName.executeQuery();
            firstMinion.next();
            System.out.println(firstMinion.getString(1));
            printMinionName.setInt(1, secondIndex--);
            ResultSet secondMinion = printMinionName.executeQuery();
            secondMinion.next();
            System.out.println(secondMinion.getString(1));
        }

    }
}
