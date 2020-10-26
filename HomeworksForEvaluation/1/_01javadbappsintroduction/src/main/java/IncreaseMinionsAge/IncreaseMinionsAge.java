package IncreaseMinionsAge;

import Interfaces.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class IncreaseMinionsAge implements Executable {
    private final String increaseMinionAge = "UPDATE `minions` AS m SET m.`age` = m.`age`+1" + System.lineSeparator() +
            "WHERE `id` = ?;";
    private final String printAllMinionNames = "SELECT m.`name`, m.`age`\n" + System.lineSeparator() +
            "FROM `minions` AS m;";

    private Scanner console;
    private Connection connection;

  public  IncreaseMinionsAge(Scanner console, Connection connection) {
        this.console = console;
        this.connection = connection;
    }

    @Override
    public void execute() {
        int[] listOfId = Arrays.stream(console.nextLine().trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        try {
            for (int i = 0; i < listOfId.length; i++) {
                PreparedStatement increaseMinionsAgeStatement = this.connection.prepareStatement(this.increaseMinionAge);
                increaseMinionsAgeStatement.setInt(1, listOfId[i]);
                increaseMinionsAgeStatement.executeUpdate();

            }

            PreparedStatement printAllMinionNamesStatement = this.connection.prepareStatement(this.printAllMinionNames);
            ResultSet printAllMinionNamesResult = printAllMinionNamesStatement.executeQuery();
            while (printAllMinionNamesResult.next()){
                System.out.printf("%s %d",printAllMinionNamesResult.getString("name"),printAllMinionNamesResult.getInt("age")).println();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
