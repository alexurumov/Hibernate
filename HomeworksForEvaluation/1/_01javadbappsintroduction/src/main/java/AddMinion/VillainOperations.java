package AddMinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VillainOperations {
    final String villainQuery = "SELECT `name` FROM `villains` WHERE `name` = ?;";
    final String insertVillainQuery = "INSERT INTO villains(name,evilness_factor) VALUES(?,'evil');";
    private final String getVillainIdQuery = "SELECT v.`id`\n" +
            "FROM villains AS v\n" +
            "WHERE v.name = ?;";
    private Connection connection;
    private String villainName;

    public VillainOperations(Connection connection, String villainName) {
        this.connection = connection;
        this.villainName = villainName;
    }

    public boolean doesVillainExists() throws SQLException {
        PreparedStatement villainQueryStatement = this.connection.prepareStatement(this.villainQuery);
        villainQueryStatement.setString(1, this.villainName);
        ResultSet villainResultSet = villainQueryStatement.executeQuery();
        return villainResultSet.next();
    }

    public void createVillain() throws SQLException {
        PreparedStatement insertVillainQueryStatement = connection.prepareStatement(this.insertVillainQuery);
        insertVillainQueryStatement.setString(1, this.villainName);
        insertVillainQueryStatement.executeUpdate();
        System.out.printf("Villain %s was added to the database", this.villainName).println();
    }

    public int getVillainId() throws SQLException {
        PreparedStatement villainIdQueryStatement = this.connection.prepareStatement(this.getVillainIdQuery);
        villainIdQueryStatement.setString(1, this.villainName);
        ResultSet villainIdResult = villainIdQueryStatement.executeQuery();
        villainIdResult.next();

        return villainIdResult.getInt("id");
    }
}
