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

public class getUserByIdTest {
        private UserDaoImpl userDao;

        @BeforeEach
        public void setup() {
            DatabaseManagerTest.connect();

            Connection connection = DatabaseManagerTest.getConnection();

            userDao = new UserDaoImpl(connection);
        }

        @AfterEach
        public void cleanup() {
            try {
                DatabaseManagerTest.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    @Test
    public void testGetUserById_ReturnsExistingUser() {
        // Set up test data
        int userId = 1; // Set the ID of an existing user

        // Perform the getUserById() operation
        User user = userDao.getUserById(userId);

        // Assert that the returned user is not null
        assertNotNull(user);

        // Assert specific user properties or ID
        assertEquals(userId, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testGetUserById_ReturnsNullForNonexistentUser() {
        int userId = 999;

        User user = userDao.getUserById(userId);

        assertNull(user);
    }
}


