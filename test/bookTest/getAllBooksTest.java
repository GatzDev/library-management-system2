package library_management.test.bookTest;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Author;
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

public class getAllBooksTest {
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
    public void allBooks() {
        Book book1 = new Book("Sample Book 1", authorDao.getAuthorById(4), 2021, "1234567890", 5);
        bookDao.addBook(book1);

        Book book2 = new Book("Sample Book 2", authorDao.getAuthorById(5), 2022, "0987654321", 3);
        bookDao.addBook(book2);

        List<Book> allBooks = bookDao.getAllBooks();

        assertEquals(2, allBooks.size());
    }

    @Test
    public void booksEmpty() {
        List<Book> allBooks = bookDao.getAllBooks();

        assertTrue(allBooks.isEmpty());
    }

}

