package IncreasedAgeStoredProcedure;

import Interfaces.Executable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreasedAgeStoredProcedure implements Executable {
    private final String callProcedureQuery = "CALL usp_get_older(?);";
    private final String printMinionName = "SELECT m.`name`, m.`age`\n" + System.lineSeparator() +
            "FROM `minions` AS m"+ System.lineSeparator()+
            "WHERE m.`id` = ?;";
    private Scanner console;
    private Connection connection;

    public IncreasedAgeStoredProcedure(Scanner console, Connection connection) {
        this.console = console;
        this.connection = connection;
    }

    @Override
    public void execute() {
        int minionId = Integer.parseInt(this.console.nextLine());
        try {
            PreparedStatement increaseMinionIdStatement = this.connection.prepareStatement(this.callProcedureQuery);
            increaseMinionIdStatement.setInt(1,minionId);
            increaseMinionIdStatement.executeUpdate();
            PreparedStatement printMinionNameStatement = this.connection.prepareStatement(this.printMinionName);
            printMinionNameStatement.setInt(1,minionId);
            ResultSet printMinionNameResult = printMinionNameStatement.executeQuery();
            while (printMinionNameResult.next()){
                System.out.printf("%s %d",printMinionNameResult.getString("name"),printMinionNameResult.getInt("age")).println();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
