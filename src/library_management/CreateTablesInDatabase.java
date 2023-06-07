package library_management;

import library_management.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


class CreateTableInDataBase {
    public static void main(String[] args) {


        try {
            System.out.println(Constants.URL);

            System.out.println("Connecting to selected database ...");
            Connection conn = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
            System.out.println("Connected to database successfully ...");
            System.out.println("Creating tables in selected database");
            createTables(conn);
            System.out.println("Tables created successfully!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement st = null;

        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(100) NOT NULL," +
                "author_id INT NOT NULL," +
                "publication_year INT NOT NULL," +
                "isbn VARCHAR(20) NOT NULL," +
                "stock BOOLEAN DEFAULT TRUE," +
                "FOREIGN KEY (author_id) REFERENCES authors(id)" +
                ")";

        String createAuthorsTable = "CREATE TABLE IF NOT EXISTS authors (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "birth_year INT NOT NULL" +
                ")";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL" +
                ")";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "book_id INT NOT NULL," +
                "borrowing_date DATE NOT NULL," +
                "return_date DATE," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (book_id) REFERENCES books(id)" +
                ")";

        try {
            st = conn.createStatement();
            {
                st.executeUpdate(createAuthorsTable);
                st.executeUpdate(createBooksTable);
                st.executeUpdate(createUsersTable);
                st.executeUpdate(createTransactionsTable);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (conn != null)
                conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

