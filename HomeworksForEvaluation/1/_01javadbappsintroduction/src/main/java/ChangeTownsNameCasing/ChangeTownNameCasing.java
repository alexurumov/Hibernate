package ChangeTownsNameCasing;

import Interfaces.Executable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeTownNameCasing implements Executable {
    private Connection connection;
    private String country;
    private Scanner console;
    private final String updateTownsByCountryQuery = "UPDATE `towns` SET `name` = UPPER( `name` )\n" +
            "WHERE `country` = ?;";
    private final String getTownByCountryQuery = "SELECT t.`name`\n" +
            "FROM `towns` AS t\n" +
            "WHERE t.`country` = ?;";

 public    ChangeTownNameCasing(Scanner console,Connection connection) {
        this.connection = connection;
        this.console = console;
    }

    private void updateTownNameByCountry() throws SQLException {
        PreparedStatement updateTownNameByCountryStatement = this.connection.prepareStatement(this.updateTownsByCountryQuery);
        updateTownNameByCountryStatement.setString(1, this.country);
        int result = updateTownNameByCountryStatement.executeUpdate();
        if (result == 0) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d towns were affected.", result).println();
            printTownNamesByCountry();
        }

    }

    private void setCountryName() {
        this.country = this.console.nextLine();
    }

    private void printTownNamesByCountry() throws SQLException {
        PreparedStatement printTownNameByCountryStatement = this.connection.prepareStatement(this.getTownByCountryQuery);
        printTownNameByCountryStatement.setString(1, this.country);
        ResultSet townNamesByCountryResult = printTownNameByCountryStatement.executeQuery();
        ArrayList<String> towns = new ArrayList<>();
        while (townNamesByCountryResult.next()){
            towns.add(townNamesByCountryResult.getString("name"));
        }
        System.out.println(towns.toString());
    }

    @Override
    public void execute() {
        this.setCountryName();
        try {
            this.updateTownNameByCountry();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
