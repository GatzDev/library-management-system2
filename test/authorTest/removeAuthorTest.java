package library_management.test.authorTest;

import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class removeAuthorTest {
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }

    @Test
    public void removeAuthor() {
        // Create a test author and add it to the database
        Author author = new Author("John Wick", 1950);
        Author addedAuthor = authorDao.addAuthor(author);

        boolean removed = authorDao.removeAuthor(addedAuthor.getId());

        // Assert  successfully removed
        assertTrue(removed);

        // Removed author from the database
        Author removedAuthor = authorDao.getAuthorById(addedAuthor.getId());

        // Removed author is null
        assertNull(removedAuthor);
    }


}