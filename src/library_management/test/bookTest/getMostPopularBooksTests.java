package library_management.test.bookTest;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.dao.TransactionDao;
import library_management.entity.Book;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.util.DatabaseManager;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class getMostPopularBooksTests {
    private BookDao bookDao;
    private AuthorDao authorDao;

    private TransactionDao transactionDao;

    @BeforeEach
    public void setup() {
        // Set up the database connection
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        authorDao = new AuthorDaoImpl(connection);
        bookDao = new BookDaoImpl(connection);
        transactionDao = new TransactionDaoImpl(connection);
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
    public void testGetMostPopularBooksEmpty() {
        List<Book> mostPopularBooks = bookDao.getMostPopularBooks(3);

        assertTrue(mostPopularBooks.isEmpty());
    }

    @Test
    public void testGetMostPopularBooks() {
        int limit = 5;

        List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

        assertFalse(popularBooks.isEmpty());
        assertEquals(limit, popularBooks.size());
    }

    @Test
    public void withZeroLimit() {
        int limit = 0;

        List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

        assertTrue(popularBooks.isEmpty());
    }

    @Test
    public void withNegativeLimit() {
        int limit = -5;

        List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

        assertTrue(popularBooks.isEmpty());
    }

    @Test
    public void withLargeLimit() {
        int limit = 100;

        List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

        assertTrue(popularBooks.size() <= limit);
    }
}


