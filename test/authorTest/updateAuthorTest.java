package library_management.test.authorTest;

import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class updateAuthorTest {
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }

    @AfterEach
    public void clean() {
        try (Statement statement = DatabaseManagerTest.getConnection().createStatement()) {
            String query = "DELETE FROM authors";
            statement.executeUpdate(query);
            System.out.println("Deleted all users from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withValidAuthor() {
        Author author = new Author("Johny Bravo", 1960);
        Author addedAuthor = authorDao.addAuthor(author);

        addedAuthor.setName("John Wick");
        addedAuthor.setBirthYear(1988);

        boolean updated = authorDao.updateAuthor(addedAuthor);

        // Assert  was successful
        assertTrue(updated);

        // Compare the updated details
        Author update = authorDao.getAuthorById(addedAuthor.getId());
        assertEquals("John Wick", update.getName());
        assertEquals(1988, update.getBirthYear());
    }

    @Test
    public void withInvalidAuthor() {
        // Create an author with an invalid ID
        Author author = new Author("John Wick", 1985);
        author.setId(-1);

        // Update the author in the database
        boolean updated = authorDao.updateAuthor(author);

        assertFalse(updated);
    }

    @Test
    public void withNullAuthor() {
        // Update a null author
        boolean updated = authorDao.updateAuthor(null);

        assertFalse(updated);

    }
}
