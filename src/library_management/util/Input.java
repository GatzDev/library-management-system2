package library_management.util;
import java.io.BufferedReader;
import java.io.IOException;

public class Input {
    public static int readIntInput(BufferedReader reader) {

        while (true) {
            try {
                String input = reader.readLine();
                return Integer.parseInt(input);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String readStringInput(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Please try again.");
            return null;
        }
    }
}
