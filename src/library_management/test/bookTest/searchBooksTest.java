package library_management.test.bookTest;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Book;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class searchBooksTest {
    private BookDao bookDao;
    private AuthorDao authorDao;

    @BeforeEach
    public void setup() {
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
    public void emptyKeyword() {
        String keyword = "";

        List<Book> searchedBooks = bookDao.searchBooks(keyword, authorDao);

        assertTrue(searchedBooks.isEmpty());
    }

    @Test
    public void nonExistingKeyword() {
        String keyword = "kij";

        List<Book> searchedBooks = bookDao.searchBooks(keyword, authorDao);

        assertTrue(searchedBooks.isEmpty());
    }

    @Test
    public void byTitle() {
        Book book = new Book("Sample Book", authorDao.getAuthorById(10), 2021, "1234567890", 5);
        bookDao.addBook(book);
        String keyword = "Sample";

        List<Book> searchedBooks = bookDao.searchBooks(keyword, authorDao);

        assertFalse(searchedBooks.isEmpty());
    }

    @Test
    public void byISBN() {
        String keyword = "1234567890";

        List<Book> searchedBooks = bookDao.searchBooks(keyword, authorDao);

        assertFalse(searchedBooks.isEmpty());
    }
}
