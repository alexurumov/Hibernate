package increaseAgeStoredProcedure;

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

        int minionId = Integer.parseInt(in.nextLine());

        String call = "{CALL usp_get_older(?)}";

        try (CallableStatement getOlder = connection.prepareCall(call)) {
            getOlder.setInt(1, minionId);
            getOlder.execute();
        }

        PreparedStatement printMinion = connection.prepareStatement("SELECT\n" +
                "    name, \n" +
                "    age\n" +
                "FROM minions\n" +
                "WHERE id = ?;");

        printMinion.setInt(1, minionId);
        ResultSet printMinionRs = printMinion.executeQuery();

        while (printMinionRs.next()) {
            System.out.printf("%s %s\n", printMinionRs.getString(1), printMinionRs.getInt(2));
        }

        connection.close();

    }
}
