package changeTownNamesCasing;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner in = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root1234");

        final String connectionURL = "jdbc:mysql://localhost:3306/minions_db";

        Connection connection = DriverManager.getConnection(connectionURL, properties);

        PreparedStatement changeNames = connection.prepareStatement("UPDATE towns\n" +
                "SET name = UPPER(name)\n" +
                "WHERE country = ?;");

        String country = in.nextLine();
        changeNames.setString(1, country);
        changeNames.execute();

        PreparedStatement townsCount = connection.prepareStatement("SELECT\n" +
                "    COUNT(id) AS 'affected'\n" +
                "FROM towns\n" +
                "WHERE country = ?;");

        townsCount.setString(1, country);
        ResultSet townsCountRs = townsCount.executeQuery();

        townsCountRs.next();
        int affected = townsCountRs.getInt("affected");

        if (affected == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.printf("%d town names were affected.\n", affected);
        PreparedStatement affectedTowns = connection.prepareStatement("SELECT\n" +
                "    name\n" +
                "FROM towns\n" +
                "WHERE country = ?;");

        affectedTowns.setString(1, country);

        ResultSet affectedRs = affectedTowns.executeQuery();

        List<String> affectedList = new ArrayList<>();

        while (affectedRs.next()) {
            affectedList.add(affectedRs.getString("name"));
        }

        System.out.println(Arrays.toString(affectedList.toArray()));

        connection.close();
    }
}
