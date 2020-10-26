

import AddMinion.AddMinion;
import ChangeTownsNameCasing.ChangeTownNameCasing;
import GetMinionsNames.GetMinionsNames;
import IncreaseMinionsAge.IncreaseMinionsAge;
import IncreasedAgeStoredProcedure.IncreasedAgeStoredProcedure;
import PrintAllMinionsNames.PrintAllMinionNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Engine implements Runnable {
    private Connection connection;
    private Scanner console;

    public Engine(Connection connection, Scanner console) {
        this.connection = connection;
        this.console = console;
    }

    public void run() {
      System.out.println("Executing problem 2:");
        try {
            this.getVillainsNames();
        } catch (SQLException e) {
         System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Please enter Data fot problem 3:");
        GetMinionsNames getMinionsNames = new GetMinionsNames(this.console,this.connection);
        getMinionsNames.execute();
        System.out.println();
        System.out.println("Please enter Data fot problem 4:");
        AddMinion addMinion = new AddMinion(this.console, this.connection);
        addMinion.execute();
        System.out.println("Please enter Data fot problem 5:");
        ChangeTownNameCasing changeTownNameCasing = new ChangeTownNameCasing(this.console,this.connection);
        changeTownNameCasing.execute();
        System.out.println();
        System.out.println("Executing problem 7:");
        PrintAllMinionNames printAllMinionNames =new PrintAllMinionNames(this.connection);
        printAllMinionNames.execute();
        System.out.println();
        System.out.println("Please enter Data fot problem 8:");
        IncreaseMinionsAge increaseMinionsAge = new IncreaseMinionsAge(this.console,this.connection);
        increaseMinionsAge.execute();
        System.out.println();
        System.out.println("Please enter Data fot problem 9:");
        IncreasedAgeStoredProcedure increasedAgeStoredProcedure = new IncreasedAgeStoredProcedure(this.console,this.connection);
        increasedAgeStoredProcedure.execute();

    }


    private void getVillainsNames() throws SQLException {

        String query = "SELECT v.name, COUNT(mv.minion_id) AS 'count'" + System.lineSeparator() +
                "FROM villains AS v" + System.lineSeparator() +
                "JOIN minions_villains mv on v.id=mv.villain_id" + System.lineSeparator() +
                "GROUP BY v.name" + System.lineSeparator() +
                "HAVING `count` > ?" + System.lineSeparator() +
                "ORDER BY `count` DESC" + System.lineSeparator();

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setInt(1, 15);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            System.out.printf("%s %d",
                    result.getString("name"),
                    result.getInt("count")).println();
        }

    }


}
