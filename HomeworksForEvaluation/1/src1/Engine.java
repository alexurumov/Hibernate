import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {

    Connection connection;

    public Engine(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        try {
            // getVillainsCount();
            //  getVillainById();
            // addMinion();
            changeTownNames();
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    //P02
    private void getVillainsCount() throws SQLException {
        String query = "SELECT v.name, count(mv.minion_id) as \"count\" FROM villains as v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY mv.villain_id\n" +
                "HAVING  count > ?\n" +
                "ORDER BY count DESC;\n";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, 15);

        ResultSet resultSet = statement.executeQuery();


        while (resultSet.next()) {
            System.out.printf("%s %d",
                    resultSet.getString("name"),
                    resultSet.getInt("count")).println();
        }
    }

    //PO3
    private void getVillainById() throws IOException, SQLException {

        String query1 = "SELECT v.name FROM villains as v WHERE v.id = ?";

        String query2 = "SELECT m.name, m.age FROM minions as m\n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int input = Integer.parseInt(bf.readLine());


        PreparedStatement statement = connection.prepareStatement(query1);
        PreparedStatement innerStatement = connection.prepareStatement((query2));

        statement.setInt(1, input);
        innerStatement.setInt(1, input);

        ResultSet resultSet = statement.executeQuery();
        ResultSet innerResultSet = innerStatement.executeQuery();


        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", input);
        } else {

            System.out.printf("Villain: %s\n", resultSet.getString("name"));

            while (innerResultSet.next()) {
                System.out.printf("%d. %s %d\n", innerResultSet.getRow(),
                        innerResultSet.getString("name"),
                        innerResultSet.getInt("age"));
            }
        }

    }

    //PO4
    private void addMinion() throws SQLException, IOException {
        boolean isEverythingOk = true;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // connection.setAutoCommit(false);

        String firstInput = bf.readLine();
        String secondInput = bf.readLine();

        String[] first = firstInput.split(" ");
        String[] second = secondInput.split(" ");

        String nameMinion = first[1];
        int ageMinion = Integer.parseInt(first[2]);
        String townMinion = first[3];

        String villain = second[1];


        String query1 = "SELECT t.id FROM towns as t Where t.name = ?";

        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setString(1, townMinion);

        ResultSet resultSet1 = statement1.executeQuery();


        if (!resultSet1.next()) {
            String query2 = "INSERT INTO towns (name) VALUE (?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setString(1, townMinion);
            preparedStatement2.executeUpdate();
            System.out.printf("Town %s was added to the database.\n", townMinion);
        }


        String query3 = "SELECT v.id FROM villains as v WHERE v.name = ?";

        PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
        preparedStatement3.setString(1, villain);
        ResultSet resultSet3 = preparedStatement3.executeQuery();

        if (!resultSet3.next()) {
            String query4 = "INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil')";
            PreparedStatement preparedStatement4 = connection.prepareStatement(query4);
            preparedStatement4.setString(1, villain);
            preparedStatement4.executeUpdate();
            System.out.printf("Villain %s was added to the database.\n", villain);
        }


        PreparedStatement preparedStatement8 = connection.prepareStatement(query1);
        preparedStatement8.setString(1, townMinion);
        ResultSet idTownResultSet = preparedStatement8.executeQuery();

        PreparedStatement preparedStatement9 = connection.prepareStatement(query3);
        preparedStatement9.setString(1, villain);
        ResultSet idVillainSet = preparedStatement9.executeQuery();
        int idTown = -1;
        while (idTownResultSet.next()) {
            idTown = idTownResultSet.getInt("id");
        }

        int idVillaini = -1;

        while (idVillainSet.next()) {
            idVillaini = idVillainSet.getInt("id");
        }

        String query5 = "INSERT INTO `minions` ( name, age, town_id) VALUES (?, ? , ?)";
        PreparedStatement preparedStatement5 = connection.prepareStatement(query5);

        preparedStatement5.setString(1, nameMinion);
        preparedStatement5.setInt(2, ageMinion);
        preparedStatement5.setInt(3, idTown);

        preparedStatement5.executeUpdate();


        String query6 = "SELECT m.id FROM minions as m \n" +
                "Where m.name = ? AND m.age = ? AND m.town_id = ?";

        PreparedStatement preparedStatement6 = connection.prepareStatement(query6);
        preparedStatement6.setString(1, nameMinion);
        preparedStatement6.setInt(2, ageMinion);
        preparedStatement6.setInt(3, idTown);

        ResultSet idNewMinionResultSet = preparedStatement6.executeQuery();

        int idNewMinion = -1;
        while (idNewMinionResultSet.next()) {
            idNewMinion = idNewMinionResultSet.getInt("id");
        }
        String query7 = "INSERT INTO `minions_villains` (minion_id, villain_id) VALUES (?, ?)";

        PreparedStatement preparedStatement7 = connection.prepareStatement(query7);

        preparedStatement7.setInt(1, idNewMinion);
        preparedStatement7.setInt(2, idVillaini);

        preparedStatement7.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s.", nameMinion, villain);

    }

    //PO5
    private void changeTownNames() throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        String countryInput = bf.readLine();

        String query = "SELECT t.name  FROM towns as t WHERE t.country = ? GROUP BY t.name";

        PreparedStatement statement1 = connection.prepareStatement(query);
        statement1.setString(1, countryInput);
        ResultSet resultSet1 = statement1.executeQuery();
        List list = new ArrayList<>();
        while (resultSet1.next()) {
            String current = resultSet1.getString("name").toUpperCase();
            String query1 = "UPDATE towns SET name = ? WHERE name = ?";

            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, current);
            statement.setString(2, current);
            statement.executeUpdate();

            list.add(current);
        }

        if(list.size() == 0) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.\n", list.size());
            System.out.println("[" + String.join(" ,", list) + "]");
        }
    }

}








