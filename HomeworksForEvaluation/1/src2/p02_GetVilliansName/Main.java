package p02_GetVilliansName;


import engine.ConnectionEngineDB;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
        Connection connection = connectionEngineDB.connectDB();

        String query = "SELECT v.name, COUNT(m.id) as `count`\n" +
                "FROM villains v\n" +
                "         JOIN minions_villains mv\n" +
                "              on v.id = mv.villain_id\n" +
                "         JOIN minions m\n" +
                "              on mv.minion_id = m.id\n" +
                "GROUP BY v.name\n" +
                "HAVING `count` > ?\n" +
                "ORDER BY `count` DESC;\n";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 15);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String name =  resultSet.getString("name");
            int counts = resultSet.getInt("count");
            System.out.println(name + " " +counts);
        }
    }
}
