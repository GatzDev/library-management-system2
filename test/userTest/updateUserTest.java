package library_management.test.userTest;

import library_management.entity.User;
import library_management.impl.UserDaoImpl;
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
        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void existingUser() {
        User user = new User( "Johny Bravo", "Bravo99@gmail.com.com");

        boolean result = userDao.updateUser(user);

        // Perform assertions to verify the user has been updated successfully
        assertTrue(result);
    }

    @Test
    public void updateUser() {
        User user = new User("Bob", "bobcho@abv.com");
        user.setId(1);

        boolean result = userDao.updateUser(user);

        // Assert that the update was successful
        assertTrue(result);
    }

    @Test
    public void nullUser() {
        boolean result = userDao.updateUser(null);

        assertFalse(result);
    }

}
