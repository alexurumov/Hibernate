package com.company;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner in = new Scanner(System.in);
        String user = "root";
        String password = "root1234";

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        PreparedStatement statement =
                connection.prepareStatement("SELECT" +
                        "    u.user_name," +
                        "    u.first_name, " +
                        "    u.last_name," +
                        "    COUNT(g.id) AS 'games_played'" +
                        "FROM users u\n" +
                        "RIGHT JOIN users_games ug\n" +
                        "    ON u.id = ug.user_id\n" +
                        "LEFT JOIN games g\n" +
                        "    ON ug.game_id = g.id\n" +
                        "WHERE u.user_name = ?;");

        String username = in.nextLine();

        statement.setString(1, username);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String user_name = rs.getString("user_name");
            if (rs.wasNull()) {
                System.out.println("No such user");
            } else {
                System.out.printf("User: %s\t\n" +
                                "%s %s has played %s games",
                        user_name,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("games_played"));
            }
        }
        connection.close();
    }
}
