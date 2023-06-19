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

import static org.junit.jupiter.api.Assertions.*;

public class searchAuthorTest {

    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }
    @Test
    public void withExistingKeyword() {
        Author author1 = new Author("John Wick", 1960);
        authorDao.addAuthor(author1);

        Author author2 = new Author("Jane Adams", 1980);
        authorDao.addAuthor(author2);

        List<Author> authors = authorDao.searchAuthor("John");

        assertEquals(1, authors.size());
        assertTrue(authors.contains(author1));
        assertFalse(authors.contains(author2));
    }

    @Test
    public void withNonExistingKeyword() {
        Author author1 = new Author("John Wick", 1960);
        authorDao.addAuthor(author1);

        Author author2 = new Author("Jane Adams", 1980);
        authorDao.addAuthor(author2);

        List<Author> authors = authorDao.searchAuthor("opa");

        assertTrue(authors.isEmpty());
    }
}
