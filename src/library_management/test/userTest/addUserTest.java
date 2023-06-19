package library_management.test.userTest;

import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class addUserTest {
    private UserDaoImpl userDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        userDao = new UserDaoImpl(connection);
    }

    @AfterEach
    public void clean() {
//        try (Statement statement = DatabaseManagerTest.getConnection().createStatement()) {
//            String query = "DELETE FROM users";
//            statement.executeUpdate(query);
//            System.out.println("Deleted all users from the database.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddUser() {
        User user = new User("John Wick", "john.whick99@gmail.com");

        userDao.addUser(user);

        List<User> users = userDao.getAllUsers();

        Assertions.assertTrue(users.contains(user));

    }


    @Test
    public void testAddUser_EmptyName_Failure() {
        User user = new User("", "john.whick99@gmail.com");

        userDao.addUser(user);

        assertFalse(userDao.userAddedToDatabase(user));
    }

    @Test
    public void testAddUser_InvalidEmail_Failure() {
        // Create a user with an invalid email
        User user = new User("John Doe", "invalid_email");

        // Call the addUser method with the user with an invalid email
        userDao.addUser(user);

        // Perform assertions to verify that the user was not added (e.g., check the database or other conditions)
        assertFalse(userDao.userAddedToDatabase(user));
    }


}