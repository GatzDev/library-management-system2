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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class getMostActiveUsersTest {
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
    public void returnsLimitedNumberOfUsers() {
        int limit = 3;

        List<User> activeUsers = userDao.getMostActiveUsers(limit);

        assertNotNull(activeUsers);

        // Assert that the number of returned active users is equal to the limit
        assertEquals(limit, activeUsers.size());
    }

    @Test
    public void returnsCorrectUserTransactionCounts() {
        int limit = 5;

        List<User> activeUsers = userDao.getMostActiveUsers(limit);

        assertNotNull(activeUsers);

        assertEquals(3, activeUsers.get(0).getTransactionCount());
        assertEquals(3, activeUsers.get(1).getTransactionCount());
        assertEquals(2, activeUsers.get(2).getTransactionCount());
    }
}

