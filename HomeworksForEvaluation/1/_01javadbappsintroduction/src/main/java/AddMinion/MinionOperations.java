package AddMinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MinionOperations {
    private final String createMinionQuery = "INSERT INTO minions(name,age,town_id) VALUES(?,?,?);";
    private final String disableKeysQuery = "ALTER TABLE `minions_villains` DISABLE KEYS;";
    private final String enableKeysQuery = "ALTER TABLE `minions_villains` ENABLE KEYS;";
    private final String createMinionRelationQuery = "INSERT INTO `minions_villains` (`minion_id`,`villain_id`) VALUES(?,?);";


    private final String getMinionIdQuery = "SELECT m.`id`\n" +
            "FROM minions AS m\n" +
            "WHERE m.name = ?;";
    private Connection connection;
    private String minionName;
    private int minionAge;
    private int townId;
    private int villainId;

    public MinionOperations(Connection connection, String minionName, int minionAge, int townId, int villainId) {
        this.connection = connection;
        this.minionName = minionName;
        this.minionAge = minionAge;
        this.townId = townId;
        this.villainId = villainId;
    }

    public void createMinion(String villainName) throws SQLException {
        PreparedStatement createMinionQueryStarement = this.connection.prepareStatement(this.createMinionQuery);
        createMinionQueryStarement.setString(1, this.minionName);
        createMinionQueryStarement.setInt(2, this.minionAge);
        createMinionQueryStarement.setInt(3, this.townId);
        createMinionQueryStarement.executeUpdate();
        this.createVillainMinionRelation();
        System.out.printf("Successfully added %s to be minion of %s", this.minionName, villainName).println();
    }

    private int getMinionId() throws SQLException {
        PreparedStatement minionIdQueryStatement = this.connection.prepareStatement(this.getMinionIdQuery);
        minionIdQueryStatement.setString(1, this.minionName);
        ResultSet minionIdResult = minionIdQueryStatement.executeQuery();
        minionIdResult.next();

        return minionIdResult.getInt("id");
    }

    private void createVillainMinionRelation() throws SQLException {
        PreparedStatement disableKeysStatement = this.connection.prepareStatement(this.disableKeysQuery);
        disableKeysStatement.executeUpdate();
        PreparedStatement createVillainMinionRelationStatement = this.connection.prepareStatement(this.createMinionRelationQuery);
        createVillainMinionRelationStatement.setInt(1, this.getMinionId());
        createVillainMinionRelationStatement.setInt(2, this.villainId);
        createVillainMinionRelationStatement.executeUpdate();
        PreparedStatement enableKeysStatement = this.connection.prepareStatement(this.enableKeysQuery);
        enableKeysStatement.executeUpdate();
    }
}
