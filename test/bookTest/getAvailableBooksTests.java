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
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class getAvailableBooksTests {
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
        try (Statement statement = DatabaseManagerTest.getConnection().createStatement()) {
            String query = "DELETE FROM books";
            statement.executeUpdate(query);
            System.out.println("Deleted all book from the database.");
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
    public void availableBooksEmpty() {
        List<Book> availableBooks = bookDao.getAvailableBooks();

        // Assert list is empty
        assertTrue(availableBooks.isEmpty());
    }

    @Test
    public void availableBooks() {
        Book book1 = new Book("Available Book 1", authorDao.getAuthorById(11), 2021, "1234567890", 1);
        bookDao.addBook(book1);

        List<Book> availableBooks = bookDao.getAvailableBooks();

        assertFalse(availableBooks.isEmpty());
    }
}