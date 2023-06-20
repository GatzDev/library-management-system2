package library_management.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManagerTest {
    private static Connection connection;

    private DatabaseManagerTest() {
        // Private constructor to prevent instantiation
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection(Constants.URL_TEST, Constants.USERNAME, Constants.PASSWORD);
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
