package library_management;
import library_management.util.FileHandler;
import library_management.util.Input;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int choice = getMenuChoice();

        if (choice == 1) {
                TerminalInterface terminalInterface = new TerminalInterface();
                terminalInterface.run();
            } else if (choice == 2) {
                library_management.CreateTableInDataBase.main(args);
                getMenuChoice();
            }else if (choice == 3) {
            System.out.println("Enter the file path to read from:");
            String readFilePath = Input.readStringInput(reader);

                try {
                FileHandler.readFromFile(readFilePath);
                }catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
                    getMenuChoice();
                }
            }else if (choice == 4) {
            System.out.println("Enter the file path to write to:");
            String writeFilePath = Input.readStringInput(reader);
            List<String> lines = new ArrayList<>();
            // Add lines to the list (e.g., from user input or other sources)
                try {
                FileHandler.writeToFile(lines, writeFilePath);
                System.out.println("File written successfully.");
                }catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                    getMenuChoice();
                }
            } else if (choice == 5) {
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
        System.out.println("3. Read From Files");
        System.out.println("4. Write To File");
        System.out.println("5. Exit");
        System.out.print("\nEnter your choice: ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
