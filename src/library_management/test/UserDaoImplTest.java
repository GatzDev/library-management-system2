package library_management.test;
import library_management.dao.UserDao;
import library_management.entity.Book;
import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import org.junit.jupiter.api.Assertions;
import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {

    private Connection connection;
    private UserDao userDao;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

        // Create an instance of the AuthorDao implementation
        userDao = new UserDaoImpl(connection);
    }

    @Test
    public void testAddUser() throws SQLException {
        // Arrange
        User user = new User("John Bravo 2", "johnBravo99@gmail.com");

        // Act
        userDao.addUser(user);

        // Assert
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE name = ? AND email = ?")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());

            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "User should be found");
                Assertions.assertEquals("John Bravo 2", user.getName(), rs.getString("name"));
                Assertions.assertEquals("johnBravo99@gmail.com", user.getEmail(), rs.getString("email"));
            }

        }
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User user = new User("Samurai Jak 2", "Jak1000@gmail.com");
        user.setId(7);

        // Act
        boolean updated = userDao.updateUser(user);

        // Assert
        assertTrue(updated);

        // Verify that the author's details have been updated correctly
        User updateUser = userDao.getUserById(user.getId());
        Assertions.assertEquals(user.getName(), updateUser.getName());
        Assertions.assertEquals(user.getEmail(), updateUser.getEmail());
    }

    @Test
    public void testRemoveUser() {
        // Arrange
        User user = new User("Jeki chan 22", "Jekito111@gmail.com");
        int userId = 7;
        userDao.addUser(user);

        // Act
        boolean result = userDao.deleteUser(userId);

        // Assert
        assertTrue(result);
        assertNull(userDao.getUserById(userId));
    }

    @Test
    public void testSearchUser() {
        // Arrange
        String keyword = "Doe";

        // Act
        List<User> searchResults = userDao.searchUsers(keyword);

        // Assert
        assertNotNull(searchResults);

        // Verify contain authors with names containing the keyword
        for (User user : searchResults) {
            assertTrue(user.getName().contains(keyword));
        }
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User("Chengis Han", "chengi1000@gmail.com");
        User user2 = new User("Tom Soier", "tomy666@gmail.com");
        userDao.addUser(user1);
        userDao.addUser(user2);

        // Act
        List<User> allUsers = userDao.getAllUsers();

        // Assert
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void testGetMostActiveUsers() {
        // Arrange
        int limit = 1;

        // Act
        List<User> mostActiveUsers = userDao.getMostActiveUsers(limit);

        // Assert
        assertNotNull(mostActiveUsers);
        assertEquals(limit, mostActiveUsers.size());

        for (User user : mostActiveUsers) {
            assertNotNull(user.getName());
        }
    }

    @Test
    public void testGetUserById() {
        // Arrange
        int userId = 2;

        // Act
        User user = userDao.getUserById(userId);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getId());

        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }
}
