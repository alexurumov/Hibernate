package GetMinionsNames;

import Interfaces.Executable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GetMinionsNames implements Executable {
    private Scanner console;
    private Connection connection;

    public GetMinionsNames(Scanner console, Connection connection) {
        this.console = console;
        this.connection = connection;
    }

    @Override
    public void execute() {
        int villainId = Integer.parseInt(this.console.nextLine());
        VillainOperations villainOperations = new VillainOperations(this.connection, villainId);


        try {
            if (villainOperations.getVillainNameById() != null) {
                LinkedHashMap<String, Integer> minionsNameAndAge;
                System.out.println(villainOperations.getVillainNameById());
                MinionOperations minionOperations = new MinionOperations(connection, villainOperations.getVillainNameById());
                minionsNameAndAge = minionOperations.getMinionsNameAndAge();
                AtomicInteger index = new AtomicInteger(1);
                minionsNameAndAge.forEach((k, v) -> {
                    System.out.printf("%s. %s %d", index.toString(), k, v).println();
                    index.incrementAndGet();
                });
            } else {
                System.out.printf("No villain with ID %d exists in the database.", villainId);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
