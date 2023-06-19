package library_management.test.authorTest;

import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class getAllAuthorsWithIdTest {
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }
    @Test
    public void getAllAuthorsWithId() {
        // Create test authors and add them to the database
        Author author1 = new Author("Guy Ritchie", 1986);
        authorDao.addAuthor(author1);

        Author author2 = new Author("Jane Smith", 1988);
        authorDao.addAuthor(author2);

        // Retrieve all authors with their IDs
        List<Author> authors = authorDao.getAllAuthorsWithId();

        // Assert that the list is not empty
        assertFalse(authors.isEmpty());

        // Assert that the list contains the added authors
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
    }
}
