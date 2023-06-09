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

public class getAllAuthorsTest {
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
    public void getAllAuthors() {
        // Create test authors and add them to the database
        Author author1 = new Author("Guy Ritchie", 1970);
        authorDao.addAuthor(author1);

        Author author2 = new Author("Jane Adams", 1980);
        authorDao.addAuthor(author2);

        List<Author> authors = authorDao.getAllAuthors();

        assertFalse(authors.isEmpty());

        //assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
    }
}

