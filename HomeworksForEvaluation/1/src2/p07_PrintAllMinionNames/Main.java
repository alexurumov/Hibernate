package p07_PrintAllMinionNames;

import engine.ConnectionEngineDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
        Connection connection = connectionEngineDB.connectDB();

        String query = "SELECT id, name FROM minions;";
        PreparedStatement namesPS = connection.prepareStatement(query);
        ResultSet namesRS = namesPS.executeQuery();

        List<String> minionsName = new ArrayList<>();
        while (namesRS.next()) {
           minionsName.add(namesRS.getString("name"));
        }

        int first = 0;
        int last =  minionsName.size() - 1;
        while (first < last){
            System.out.println(minionsName.get(first));
            System.out.println(minionsName.get(last));

            first ++;
            last --;
        }
    }
}
