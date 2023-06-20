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

public class removeBookTests {

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
    public void WithExistingId() {
        Author author = new Author("John Doe", 1980);
        authorDao.addAuthor(author);

        Book book = new Book("Sample Book 33", authorDao.getAuthorById(10), 2021, "1234567890", 5);
        bookDao.addBook(book);

        int bookId = book.getId();

        // Remove from  database
        boolean removed = bookDao.removeBook(bookId);

        // Assert removed successfully
        assertTrue(removed);
    }

    @Test
    public void NonExistingId() {
        boolean removed = bookDao.removeBook(999);
        assertFalse(removed);
    }

    @Test
    public void testRemoveBookWithInvalidId() {
        int BookId = -1;

        boolean removed = bookDao.removeBook(BookId);

        assertFalse(removed);
    }
}


