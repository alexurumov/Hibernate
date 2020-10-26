package p08_IncreaseMinionAge;

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
        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
         connection = connectionEngineDB.connectDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().split("\\s+");
        String indexes = String.join(", ", input);

        updateMinionsAge(indexes);
        printMinions();
    }

    private static void printMinions() throws SQLException {
        String  query = "SELECT name, age FROM minions";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        StringBuilder sb = new StringBuilder();
        while (resultSet.next()){

            sb.append(resultSet.getString(1)).append(" ").append(resultSet.getInt(2)).append("\n");
        }

        System.out.print(sb);
    }

    private static void updateMinionsAge(String indexes) throws SQLException {
        String query =
                "UPDATE minions\n" +
                        "SET\n" +
                        "    name = LOWER(name),\n" +
                        "    age = age + 1\n" +
                        "WHERE Id IN("+ indexes +");";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }
}
