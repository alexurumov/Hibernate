package PrintAllMinionsNames;

import Interfaces.Executable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrintAllMinionNames implements Executable {
    private final String getMinionsNamamesQuery = "SELECT m.`name`\n" +
            "FROM `minions` AS m\n";
    private Connection connection;

  public  PrintAllMinionNames(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void execute() {

        try {
            PreparedStatement getMinionsNamesStatement = this.connection.prepareStatement(this.getMinionsNamamesQuery);
            ResultSet minionsNamesResult = getMinionsNamesStatement.executeQuery();
            ArrayList<String> minionsNames = new ArrayList<>();
            while(minionsNamesResult.next()){

                minionsNames.add(minionsNamesResult.getString("name"));

            }
            for(int n=0;n<minionsNames.size()/2;n++){
                System.out.println(minionsNames.get(n));
                System.out.println(minionsNames.get(minionsNames.size()-n-1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
