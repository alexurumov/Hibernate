package GetMinionsNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VillainOperations {
    private final String selectVillainQuery = "SELECT v.`name`\n" +
            "FROM `villains` AS v\n" +
            "WHERE v.`id` = ?;";
    private Connection connection;
    private int villainId;

    public VillainOperations(Connection connection, int villainId) {
        this.connection = connection;
        this.villainId = villainId;
    }

    public String getVillainNameById() throws SQLException {
        PreparedStatement getVillainByIdQueryStatement = this.connection.prepareStatement(this.selectVillainQuery);
        getVillainByIdQueryStatement.setInt(1, this.villainId);
        ResultSet villainNameResult = getVillainByIdQueryStatement.executeQuery();
        if(villainNameResult.next())
        {
            return villainNameResult.getString("name");
        }else return null;

    }



}
