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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class getMostProlificAuthorsTest {
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        authorDao = new AuthorDaoImpl(connection);
    }

    @Test
    public void mostProlificAuthorsWithLimit() {
        Author author1 = new Author("John Doe", 1980);
        author1.setBookCount(10);
        authorDao.addAuthor(author1);

        Author author2 = new Author("Jane Smith", 1990);
        author2.setBookCount(5);
        authorDao.addAuthor(author2);

        Author author3 = new Author("Mike Johnson", 1975);
        author3.setBookCount(15);
        authorDao.addAuthor(author3);

        List<Author> prolificAuthors = authorDao.getMostProlificAuthors(2);

        // Assert  expected number of authors
        assertEquals(2, prolificAuthors.size());

        // Assert that the authors are in the correct order based on book count
        assertEquals(author3, prolificAuthors.get(0));
        assertEquals(author1, prolificAuthors.get(1));
    }

    @Test
    public void mostProlificAuthorsWithZeroLimit() {
        List<Author> prolificAuthors = authorDao.getMostProlificAuthors(0);

        // Assert list is empty
        assertTrue(prolificAuthors.isEmpty());
    }
}
