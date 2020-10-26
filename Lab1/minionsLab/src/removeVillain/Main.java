package removeVillain;

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

        int villainId = Integer.parseInt(in.nextLine());

        try {
            connection.setAutoCommit(false);

            PreparedStatement selectVillain = connection.prepareStatement("SELECT name\n" +
                    "FROM villains\n" +
                    "WHERE id = ?; ");

            selectVillain.setInt(1, villainId);
            ResultSet selectVillainRs = selectVillain.executeQuery();

            if (!selectVillainRs.next()) {
                System.out.println("No such villain was found");
                return;
            }

            PreparedStatement releasedMinions = connection.prepareStatement("SELECT COUNT(minion_id) AS 'released'\n" +
                    "FROM minions_villains\n" +
                    "WHERE villain_id = ?;");

            releasedMinions.setInt(1, villainId);
            ResultSet releasedRs = releasedMinions.executeQuery();
            releasedRs.next();
            int released = releasedRs.getInt(1);

            PreparedStatement deleteRelations = connection.prepareStatement("DELETE \n" +
                    "FROM minions_villains\n" +
                    "WHERE villain_id = ?;");

            deleteRelations.setInt(1, villainId);
            deleteRelations.execute();

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE\n" +
                    "FROM villains\n" +
                    "WHERE id = ?;");

            deleteStatement.setInt(1, villainId);
            deleteStatement.execute();

            String villainName = selectVillainRs.getString("name");
            System.out.println(villainName + " was deleted");
            System.out.println(released + " minions released");

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
        }
    }
}
