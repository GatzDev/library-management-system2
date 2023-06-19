package library_management.test.userTest;

import library_management.impl.UserDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class deleteuserTest {
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
    public void testDeleteUser_ValidUserId() {
        int userIdToDelete = 22;

        boolean result = userDao.deleteUser(userIdToDelete);

        assertTrue(result);
    }

    @Test
    public void testDeleteUser_InvalidUserId() {
        int invalidUserId = 999;

        boolean result = userDao.deleteUser(invalidUserId);

        // Assert
        assertFalse(result);
    }
}

