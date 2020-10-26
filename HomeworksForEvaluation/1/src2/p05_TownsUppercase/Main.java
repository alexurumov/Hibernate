package p05_TownsUppercase;

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

        String country = readingInput();
        ResultSet resultSet =  getTownsCollections(country);
        if (resultSet.isBeforeFirst()){
            printCountsOfTownsAndTowns(resultSet);
        } else {
            System.out.println("No town names were affected.");
        }

    }

    private static void printCountsOfTownsAndTowns(ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(" town names were affected.\n[");
        int counter = 0;
        while (resultSet.next()){
            counter++;
            sb.append(resultSet.getString(1).toUpperCase()).append(", ");
        }
        sb.insert(0, counter);
        sb.delete(sb.length() -2, sb.length());
        sb.append("]");
        System.out.println(sb);
    }

    private static ResultSet getTownsCollections(String country) throws SQLException {
        String query = "SELECT name FROM towns\n" +
                "WHERE country LIKE ?;";
        PreparedStatement townPS = connection.prepareStatement(query);
        townPS.setString(1, country);
        return townPS.executeQuery();
    }

    private static String readingInput() throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       return reader.readLine();
    }
}
