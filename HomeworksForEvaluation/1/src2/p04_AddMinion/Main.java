package p04_AddMinion;

import engine.ConnectionEngineDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] minionData = reader.readLine().split("\\s+");

        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];
        String villainName = reader.readLine().split("\\s+")[1];

        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
        connection = connectionEngineDB.connectDB();

        if (isEntityExist(minionTown, "towns")) {
            insertTown(minionTown);
        }

        if (isEntityExist(villainName, "villains")) {
            insertVillain(villainName);
        }

        int townId = getEntityId(minionTown, "towns");
        int villainId = getEntityId(villainName, "villains");
        insertMinion(minionName, minionAge, townId);
        int minionId = getEntityId(minionName, "minions");
        hireMinion(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);
    }


    private static boolean isEntityExist(String name, String tableName) throws SQLException {
        String query = "SELECT name FROM " + tableName + " WHERE name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        return !resultSet.next();
    }

    private static void insertTown(String minionTown) throws SQLException {
        String insertQuery = "INSERT INTO towns(name, country) VALUES(?, 'Vsichko Bylgarsko')";
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        insertStatement.setString(1, minionTown);
        insertStatement.execute();
        System.out.printf("Town %s was added to the database.", minionTown).println();
    }

    private static void insertVillain(String villainName) throws SQLException {
        String insertQuery = "INSERT INTO villains(name, evilness_factor) VALUES(?, 'evil')";
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        insertStatement.setString(1, villainName);
        insertStatement.execute();
        System.out.printf("Villain %s was added to the database.", villainName).println();
    }

    private static int getEntityId(String name, String tableName) throws SQLException {
        String query = "SELECT id FROM "+ tableName +" WHERE name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return  resultSet.getInt("id");
    }

    private static void insertMinion(String minionName, int age, int townId) throws SQLException {
        String query = "INSERT INTO minions(name, age, town_id) VALUES (?, ?, ?);";
        PreparedStatement minionsInsertStatement = connection.prepareStatement(query);
        minionsInsertStatement.setString(1, minionName);
        minionsInsertStatement.setInt(2, age);
        minionsInsertStatement.setInt(3, townId);

        minionsInsertStatement.execute();
    }

    private static void hireMinion(int minionId, int villainId) throws SQLException {
        String query = String.format("INSERT INTO minions_villains(minion_id,villain_id) VALUES(%d,%d)", minionId, villainId);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }
}
