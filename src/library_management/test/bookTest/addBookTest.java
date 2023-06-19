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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class addBookTest {
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
    public void clean() {
//        try (Statement statement = DatabaseManagerTest.getConnection().createStatement()) {
//            String query = "DELETE FROM books";
//            statement.executeUpdate(query);
//            System.out.println("Deleted all book from the database.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            DatabaseManagerTest.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WithValidData() {
        Author author = new Author("Test Book", 1980);
        authorDao.addAuthor(author);

        Book book = new Book("Sample Book 5", authorDao.getAuthorById(4), 2020, "34534545443", 1);
        boolean added = bookDao.addBook(book);

        assertTrue(added);
    }

    @Test
    public void WithNonExistingAuthor() {

        Book book = new Book("Test Book 2", authorDao.getAuthorById(100), 2021, "876876865675", 1);
        boolean added = bookDao.addBook(book);

        assertFalse(added);
    }

    @Test
    public void WithNegativePublicationYear() {
        Book book = new Book("Book Title 3", authorDao.getAuthorById(10), -2022, "9781234567890", 1);
        boolean added = bookDao.addBook(book);
        assertFalse(added);
    }

    @Test
    public void WithStockZero() {
        Book book = new Book("Book Title 6", authorDao.getAuthorById(10), 2022, "9781234567890", 0);
        boolean added = bookDao.addBook(book);
        assertFalse(added);

    }

    @Test
    public void WithEmptyTitle() {
        Book book = new Book("", authorDao.getAuthorById(10), 2022, "474545654654", 1);
        boolean added = bookDao.addBook(book);
        assertFalse(added);
    }
}
