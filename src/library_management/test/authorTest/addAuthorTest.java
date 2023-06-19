package library_management.test.authorTest;

import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class addAuthorTest {
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManager.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        // Create an instance of the AuthorDao implementation
        authorDao = new AuthorDaoImpl(connection);
    }

    @Test
    public void validAuthor() {
        Author validAuthor = new Author("John Wick 2", 1999);
        Author addedAuthor = authorDao.addAuthor(validAuthor);

        assertNotNull(addedAuthor);
        assertEquals(validAuthor.getName(), addedAuthor.getName());
        assertEquals(validAuthor.getBirthYear(), addedAuthor.getBirthYear());
        assertTrue(authorDao.authorExistsInDatabase(validAuthor));
    }

    @Test
    public void nullAuthor() {
        Author nullAuthor = null;
        Author addedAuthor = authorDao.addAuthor(nullAuthor);

        assertNull(addedAuthor);
    }

    @Test
    public void nullName() {
        Author authorHavingNullName = new Author(null, 1975);
        Author addedAuthor = authorDao.addAuthor(authorHavingNullName);

        assertNull(addedAuthor);
    }

    @Test
    public void emptyName() {
        Author authorHavingEmptyName = new Author("", 1960);
        Author addedAuthor = authorDao.addAuthor(authorHavingEmptyName);

        assertNull(addedAuthor);
    }

    @Test
    public void invalidBirthYear() {
        Author authorHavingInvalidBirthYear = new Author("Adam Smith", LocalDate.now().getYear() + 1);
        Author addedAuthor = authorDao.addAuthor(authorHavingInvalidBirthYear);

        assertNull(addedAuthor);
    }

    @Test
    public void negativeBirthYear() {
        Author authorHavingNegativeBirthYear = new Author("Misho Birata", -1995);
        Author addedAuthor = authorDao.addAuthor(authorHavingNegativeBirthYear);

        assertNull(addedAuthor);
    }
}







