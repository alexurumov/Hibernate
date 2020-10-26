package GetMinionsNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class MinionOperations {
    private final String getMinionsNameQuery = "SELECT m.name, m.age\n" +
            "FROM minions_villains AS mv\n" +
            "INNER JOIN minions m on mv.minion_id = m.id\n" +
            "INNER JOIN villains v on mv.villain_id = v.id\n" +
            "WHERE v.name = ?\n";
    private Connection connection;
    private String villainName;
    MinionOperations(Connection connection, String villainName){
        this.connection=connection;
        this.villainName=villainName;
    }
   public LinkedHashMap<String,Integer> getMinionsNameAndAge() throws SQLException {
       PreparedStatement getMinionsNameAndAgeStatement = this.connection.prepareStatement(this.getMinionsNameQuery);
       LinkedHashMap <String,Integer> minionsNameAndAge = new LinkedHashMap<>();
       getMinionsNameAndAgeStatement.setString(1,this.villainName);
       ResultSet minionsNameAndAgeResult = getMinionsNameAndAgeStatement.executeQuery();
       while (minionsNameAndAgeResult.next()){
           minionsNameAndAge.putIfAbsent(minionsNameAndAgeResult.getString("name"),minionsNameAndAgeResult.getInt("age"));

       }
       return minionsNameAndAge;
   }
}
