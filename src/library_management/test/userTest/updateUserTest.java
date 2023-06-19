package library_management.test.userTest;

import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class updateUserTest {
    private UserDaoImpl userDao;

    @BeforeEach
    public void setup() {
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        userDao = new UserDaoImpl(connection);
    }

    @AfterEach
    public void cleanup() {
        // Close the connection to the database
        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateUser_ExistingUser_Success() {
        // Create a mock user with an existing ID
        User user = new User( "John Doe", "john@example.com");

        // Call the updateUser method
        boolean result = userDao.updateUser(user);

        // Perform assertions to verify the user has been updated successfully
        assertTrue(result);
    }

    @Test
    public void testUpdateUser_Success() {
        // Create a mock User object
        User user = new User("John", "john@example.com");
        user.setId(1); // Set the ID of the user

        // Perform the update operation
        boolean result = userDao.updateUser(user);

        // Assert that the update was successful
        assertTrue(result);
    }

    @Test
    public void testUpdateUser_NullUser() {
        boolean result = userDao.updateUser(null);

        // Assert that the update failed
        assertFalse(result);
    }

}
