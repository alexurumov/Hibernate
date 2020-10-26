package addMinion;

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

        String[] minionData = in.nextLine().split("\\s+");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];
        String villain = in.nextLine().split("\\s+")[1];


        try {
            connection.setAutoCommit(false);

            PreparedStatement townStatement = connection.prepareStatement("SELECT id\n" +
                    "FROM towns\n" +
                    "WHERE name = ?;");

            townStatement.setString(1, minionTown);
            ResultSet townResult = townStatement.executeQuery();

            if (!townResult.next()) {
                //INSERT TOWN
                PreparedStatement insertTown = connection.prepareStatement("INSERT INTO towns(name)\n" +
                        "VALUES (?);");
                insertTown.setString(1, minionTown);
                insertTown.execute();
                townResult = townStatement.executeQuery();
                townResult.next();
                System.out.printf("Town %s was added to the database.\n", minionTown);
            }

            PreparedStatement villainStatement = connection.prepareStatement("SELECT id\n" +
                    "FROM villains\n" +
                    "WHERE name = ?;");

            villainStatement.setString(1, villain);
            ResultSet villainResult = villainStatement.executeQuery();

            if (!villainResult.next()) {
                //INSERT VILLAIN
                PreparedStatement insertVillain = connection.prepareStatement("INSERT INTO villains(name, evilness_factor)\n" +
                        "VALUES (?, 'evil');");
                insertVillain.setString(1, villain);
                insertVillain.execute();
                villainResult = villainStatement.executeQuery();
                villainResult.next();
                System.out.printf("Villain %s was added to the database.\n", villain);
            }

            PreparedStatement addMinion = connection.prepareStatement("INSERT INTO minions(name, age, town_id)\n" +
                    "VALUES (?, ?, ?);");

            addMinion.setString(1, minionName);
            addMinion.setInt(2, minionAge);
            addMinion.setInt(3, townResult.getInt("id"));
            addMinion.execute();

            PreparedStatement minionId = connection.prepareStatement("SELECT id\n" +
                    "FROM minions\n" +
                    "WHERE name = ?;");

            minionId.setString(1, minionName);
            ResultSet minionResult = minionId.executeQuery();

            PreparedStatement addRelation = connection.
                    prepareStatement("INSERT INTO minions_villains(minion_id, villain_id)\n" +
                    "VALUES (?, ?);");

            minionResult.next();
            addRelation.setInt(1, minionResult.getInt("id"));
            addRelation.setInt(2, villainResult.getInt("id"));
            addRelation.execute();

            connection.commit();

            System.out.printf("Successfully added %s to be minion of %s.\n", minionName, villain);

            connection.close();

        } catch (SQLException e) {
            connection.rollback();
        }

    }
}
