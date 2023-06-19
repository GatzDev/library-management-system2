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

import static org.junit.jupiter.api.Assertions.*;

public class updateBookTest {
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
            System.out.println("Deleted all books from the database.");
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
    public void WithValidData() {
//        Author author = new Author("Vlado Nas", 1980);
//        authorDao.addAuthor(author);

        Book book = new Book("Sample Book 2", authorDao.getAuthorById(10), 2021, "1234567890", 1);
        bookDao.addBook(book);
        int bookId = book.getId();

        book.setTitle("Updated Book");
        book.setPublicationYear(2022);
        book.setISBN("9876543210");
        book.setStock(10);

        book.setId(bookId);
        boolean updated = bookDao.updateBook(book);

        Book updatedBook = bookDao.getBookById(bookId);

        assertTrue(updated);
        assertEquals("Updated Book", updatedBook.getTitle());
        assertEquals(2022, updatedBook.getPublicationYear());
        assertEquals("9876543210", updatedBook.getISBN());
        assertEquals(10, updatedBook.getStock());
    }

    @Test
    public void NonExistingBook() {
        Book nonExisting = new Book("Sample Book 2", authorDao.getAuthorById(10), 2021, "1234567890", 5);
        bookDao.addBook(nonExisting);
        nonExisting.setId(1000);

        boolean updated = bookDao.updateBook(nonExisting);

        assertFalse(updated);
    }

    @Test
    public void invalidISBN() {

        Book book = new Book("Sample Book 3", authorDao.getAuthorById(10), 2021, "1234567890", 5);
        bookDao.addBook(book);
        int bookId = book.getId();

        book.setISBN("LJNDSGFDSFL");

        book.setId(bookId);

        boolean updated = bookDao.updateBook(book);

        assertFalse(updated);
    }

    @Test
    public void withZeroStock() {
        Book book = new Book("Sample Book 4", authorDao.getAuthorById(10), 2021, "1234567890", 5);
        bookDao.addBook(book);
        int bookId = book.getId();

        // Use the setStock method to set the stock value
        book.setStock(-1);

        book.setId(bookId);

        boolean updated = bookDao.updateBook(book);

        assertFalse(updated);
    }

}
