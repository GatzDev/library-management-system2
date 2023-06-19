package library_management.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;

    private DatabaseManager() {
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

