package increaseMinionsAge;

import java.sql.*;
import java.util.Arrays;
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

        int[] minionIds = Arrays.stream(in.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        PreparedStatement updateMinionAge = connection.prepareStatement("UPDATE minions\n" +
                "SET age = age + 1\n" +
                "WHERE id = ?;");

        PreparedStatement updateMinionName = connection.prepareStatement("UPDATE minions\n" +
                "SET name = LOWER(name)\n" +
                "WHERE id = ?;");

        for (int minionId : minionIds) {
            updateMinionAge.setInt(1, minionId);
            updateMinionAge.execute();
            updateMinionName.setInt(1, minionId);
            updateMinionName.execute();
        }

        PreparedStatement printAllMinions = connection.prepareStatement("SELECT\n" +
                "    name,\n" +
                "    age\n" +
                "FROM minions;");

        ResultSet allMinions = printAllMinions.executeQuery();

        while (allMinions.next()) {
            System.out.printf("%s %s\n", allMinions.getString(1), allMinions.getInt(2));
        }

        connection.close();

    }
}
