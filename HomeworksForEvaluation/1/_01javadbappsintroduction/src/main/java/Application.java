import java.util.Scanner;


public class Application {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter username default (root): ");
        String user = console.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();
        System.out.print("Enter password default (empty):");
        String password = console.nextLine(
        ).trim();
        System.out.println();
        ConnectionManager connectionManager = new ConnectionManager(user,password);
        Engine engine = new Engine(connectionManager.getConnection(),console);
        engine.run();
    }
}
