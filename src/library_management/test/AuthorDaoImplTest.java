package library_management.test;

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

public class AuthorDaoImplTest {

    private Connection connection;
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }

    @Test
    public void testAddAuthor() throws SQLException {

        Author author = new Author("Test Name", 1980);

        authorDao.addAuthor(author);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM authors WHERE name = ? AND birth_year = ?")) {
            stmt.setString(1, author.getName());
            stmt.setInt(2, author.getBirthYear());

            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Author should be found");
                Assertions.assertEquals("Test Name", author.getName(), rs.getString("name"));
                Assertions.assertEquals(Float.parseFloat("1980"), author.getBirthYear(), rs.getInt("birth_year"));
            }
        }
    }

    @Test
    public void testUpdateAuthor() {
        // Arrange
        Author author = new Author("John Doe", 1988);
        author.setId(5);

        // Act
        boolean updated = authorDao.updateAuthor(author);

        // Assert
        assertTrue(updated);

        // Verify that the author's details have been updated correctly
        Author updatedAuthor = authorDao.getAuthorById(author.getId());
        Assertions.assertEquals(author.getName(), updatedAuthor.getName());
        Assertions.assertEquals(author.getBirthYear(), updatedAuthor.getBirthYear());
    }

    @Test
    public void testRemoveAuthor() {
        // Arrange
        Author author = new Author("Roger Zelazni", 1923);
        int authorId = 11;
        authorDao.addAuthor(author);

        // Act
        boolean result = authorDao.removeAuthor(authorId);

        // Assert
        assertTrue(result);
        assertNull(authorDao.getAuthorById(authorId));
    }

    @Test
    public void testSearchAuthor() {
        // Arrange
        String keyword = "Doe";

        // Act
        List<Author> searchResults = authorDao.searchAuthor(keyword);

        // Assert
        assertNotNull(searchResults);

        // Verify contain authors with names containing the keyword
        for (Author author : searchResults) {
            assertTrue(author.getName().contains(keyword));
        }
    }
}

