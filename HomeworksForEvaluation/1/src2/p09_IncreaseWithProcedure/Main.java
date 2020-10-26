package p09_IncreaseWithProcedure;

import engine.ConnectionEngineDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws IOException, SQLException {
        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
        connection = connectionEngineDB.connectDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int inputId = Integer.parseInt(reader.readLine());

        callPredicate(inputId);
        printMinion(inputId);
    }

    private static void printMinion(int inputId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions WHERE id = "+ inputId + ";");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
    }

    private static void callPredicate(int inputId) throws SQLException {
        CallableStatement predicateCall = connection.prepareCall("{CALL minions_db.usp_get_older(" + inputId + ")}");
        predicateCall.executeUpdate();
        predicateCall.close();

    }
}
