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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class searchUsersTest {
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
    public void returnsEmptyListNoMatches() {
        String keyword = "Alice";

        List<User> userList = userDao.searchUsers(keyword);

        assertTrue(userList.isEmpty());
    }

    @Test
    public void returnsMatchingUsers() {
        User user = new User("Turbo22", "turrro@gmail.com");
        userDao.addUser(user);

        String keyword = "tur"; // Set  search
        int expectedUserCount = 5; // Set number of matching users

        List<User> userList = userDao.searchUsers(keyword);

        assertEquals(expectedUserCount, userList.size());
        assertEquals("Turbo22", userList.get(0).getName());
        assertEquals("turrro@gmail.com", userList.get(0).getEmail());
    }
}

