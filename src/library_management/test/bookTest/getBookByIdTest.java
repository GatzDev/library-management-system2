package library_management.test.bookTest;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class getBookByIdTest {
    private BookDao bookDao;
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        authorDao = new AuthorDaoImpl(connection);
        bookDao = new BookDaoImpl(connection);
    }

    @AfterEach
    public void cleanup() {
        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetExistingBookById() {
        int bookId = 53;

        Book book = bookDao.getBookById(bookId);

        assertNotNull(book);
        assertEquals(bookId, book.getId());
    }

    @Test
    public void withInvalidId() {
        int invalidBookId = -1;

        Book book = bookDao.getBookById(invalidBookId);

        assertNull(book);
    }

    @Test
    public void withZeroId() {
        int zeroBookId = 0;

        Book book = bookDao.getBookById(zeroBookId);

        assertNull(book);
    }

    @Test
    public void nonExistingId() {
        Book retrievedBook = bookDao.getBookById(999);

        assertNull(retrievedBook);
    }
}

