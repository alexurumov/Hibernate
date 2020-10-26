package p03_GetMinionNames;

import engine.ConnectionEngineDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        int villainId = Integer.parseInt(reader.readLine());

        ConnectionEngineDB connectionEngineDB = new ConnectionEngineDB();
        Connection connection = connectionEngineDB.connectDB();

        String queryVillain = "SELECT name\nFROM villains\nwhere id = ?;";
        PreparedStatement villainPreparedState = connection.prepareStatement(queryVillain);
        villainPreparedState.setInt(1, villainId);
        ResultSet rsVillain = villainPreparedState.executeQuery();

        if (!rsVillain.next()){
            System.out.printf("No villain with ID %d exists in the database.\n", villainId);
            return;
        }

        String queryMinions = String.format("SELECT m.name, m.age\nFROM villains v\nJOIN minions_villains mv on v.id = mv.villain_id\nJOIN minions m on mv.minion_id = m.id\nwhere v.id = ?;");
        PreparedStatement minionsPS = connection.prepareStatement(queryMinions);
        minionsPS.setInt(1, villainId);
        ResultSet rsMinions = minionsPS.executeQuery();

        StringBuilder printer = new StringBuilder();
        printer.append("Villain: ").append(rsVillain.getString("name")).append("\n");

        int counter = 0;
        while (rsMinions.next()){
            printer.append(String.format("%d. %s %d",++counter,
                    rsMinions.getString("name"),
                    rsMinions.getInt("age"))).append("\n");
        }

        System.out.print(printer);
    }
}
