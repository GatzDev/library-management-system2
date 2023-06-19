package library_management.test.userTest;

import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class getAllUsersTest {
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
    public void allUsers() {
        List<User> userList = userDao.getAllUsers();

        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }
    @Test
    public void returnsCorrectUserCount() {
        List<User> userList = userDao.getAllUsers();

        int expectedUserCount = 75; // Set the expected user !!!
        assertEquals(expectedUserCount, userList.size());
    }
}


