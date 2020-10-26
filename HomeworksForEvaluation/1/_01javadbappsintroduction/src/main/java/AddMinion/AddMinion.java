package AddMinion;

import Interfaces.Executable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMinion implements Executable {
    private Scanner console;
    private Connection connection;

    public AddMinion(Scanner console, Connection connection) {
        this.console = console;
        this.connection = connection;
    }

    @Override
    public void execute() {
        String[] minionData = this.console.nextLine().split("\\s+");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];
        String villainName = console.nextLine().split("\\s+")[1];
        try {
            TownOperations townOperations = new TownOperations(connection, minionTown);
            if (!townOperations.doesTownExists()) {
                townOperations.createTown();
            }
            VillainOperations villainOperations = new VillainOperations(connection, villainName);
            if (!villainOperations.doesVillainExists()) {
                villainOperations.createVillain();
            }
            MinionOperations minionOperations = new MinionOperations(connection, minionName, minionAge,
                    townOperations.getTownId(), villainOperations.getVillainId());
            minionOperations.createMinion(villainName);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}