package library_management;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            int choice = getMenuChoice();

            if (choice == 1) {
                TerminalInterface terminalInterface = new TerminalInterface();
                terminalInterface.run();
            } else if (choice == 2) {
                library_management.CreateTableInDataBase.main(args);
                getMenuChoice();
            } else if (choice == 3) {
                System.out.println("Exiting the application...  Goodbye!");
            } else {
                System.out.println("Invalid choice. Please try again.");
                getMenuChoice();
            }
        }



    private static int getMenuChoice() {
        System.out.println("\nWelcome to the Library System!\n");
        System.out.println("Choose an option:");
        System.out.println("1. Run Terminal Interface");
        System.out.println("2. Create Database Tables");
        System.out.println("3. Exit");
        System.out.print("\nEnter your choice: ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
