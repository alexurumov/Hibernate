package AddMinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class TownOperations {
    private final String townQuery = "SELECT `name` FROM `towns` WHERE `name` = ?;";
    private final String insertTownQuery = "INSERT INTO towns(name) VALUES(?);";
    private final String getTownIdQuery = "SELECT t.`id`\n" +
            "FROM towns AS t\n" +
            "WHERE t.name = ?;";
    private String minionTown;
    private Connection connection;

    public TownOperations(Connection connection, String minionTown)
    {
        this.connection=connection;
        this.minionTown = minionTown;
    }

    public boolean doesTownExists() throws SQLException {
        PreparedStatement townQueryStatement = this.connection.prepareStatement(this.townQuery);
        townQueryStatement.setString(1, this.minionTown);
        ResultSet townResultSet = townQueryStatement.executeQuery();
        return townResultSet.next();
    }

    public void createTown() throws SQLException {
        PreparedStatement insertTownQueryStatement = this.connection.prepareStatement(this.insertTownQuery);
        insertTownQueryStatement.setString(1, this.minionTown);
        insertTownQueryStatement.executeUpdate();
        System.out.printf("Town %s was added to the database",this.minionTown).println();
    }
    public int getTownId() throws SQLException {
        PreparedStatement townIdQueryStatement = this.connection.prepareStatement(this.getTownIdQuery);
        townIdQueryStatement.setString(1, this.minionTown);
        ResultSet townIdResult = townIdQueryStatement.executeQuery();
        townIdResult.next();

        return townIdResult.getInt("id");
    }
}
