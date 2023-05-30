package library_management.Test;

import library_management.Dao.BookDao;
import library_management.entity.Book;
import library_management.impl.BookDaoImpl;
import library_management.util.DatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDaoImplTest {
    private static final String URL = "jdbc:mysql://localhost/data_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "L31mz40123!";
    private Connection connection;
    private BookDao bookDao;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // Create an instance of the BookDao implementation
        bookDao = new BookDaoImpl(connection);
    }

    @Test
    public void getAvailableBooks() {
        // Create an instance of the BookDaoImpl
        BookDaoImpl bookDao = new BookDaoImpl(connection);

        // Call the method to get available books
        List<Book> availableBooks = bookDao.getAvailableBooks();

        // Perform assertions to verify the results
        Assertions.assertNotNull(availableBooks, "List of available books should not be null");
        Assertions.assertFalse(availableBooks.isEmpty(), "List of available books should not be empty");

        // You can add more specific assertions based on your requirements

        // Print the available books
        System.out.println("Available Books:");
        for (Book book : availableBooks) {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            // Print other book details as needed
            System.out.println();
        }
    }

    // Add more tests for other methods in BookDaoImpl

    @Test
    public void testRemoveBook() {
        // Create an instance of the BookDaoImpl
        BookDaoImpl bookDao = new BookDaoImpl(connection);

        // Create a test book
        Book book = new Book(17, "Test sdfdsfds", 2, 2023, "949393erfs5969", 1);

        // Add the test book to the database
        bookDao.addBook(book);

        // Get the book ID of the test book
        int bookId = book.getId();

        // Call the method to remove the book
        boolean removed = bookDao.removeBook(bookId);

        // Perform assertions to verify the result
        Assertions.assertTrue(removed, "Book should be successfully removed");

        // Try to get the removed book from the database
        Book removedBook = bookDao.getBookById(bookId);

        // Verify that the removed book is null
        Assertions.assertNull(removedBook, "Removed book should be null");
    }

    @Test
    public void GetAllBooks() {
        // Create an instance of the BookDaoImpl
        BookDaoImpl bookDao = new BookDaoImpl(connection);

        // Add some test books to the database
        Book book1 = new Book(1, "Book 1", 1, 2022, "ISBN1", 5);
        Book book2 = new Book(2, "Book 2", 2, 2023, "ISBN2", 3);
        bookDao.addBook(book1);
        bookDao.addBook(book2);

        // Call the method to get all books
        List<Book> books = bookDao.getAllBooks();

        // Perform assertions to verify the result
        Assertions.assertEquals(2, books.size(), "Incorrect number of books retrieved");
        Assertions.assertTrue(books.contains(book1), "Book 1 should be present in the list");
        Assertions.assertTrue(books.contains(book2), "Book 2 should be present in the list");
    }

    @Test
    public void GetBookById() {
        // Create an instance of the BookDaoImpl
        BookDaoImpl bookDao = new BookDaoImpl(connection);

        // Create a test book
        Book book = new Book(34, "Test 4565456", 1, 2023, "ISBN123", 1);

        // Add the test book to the database
        bookDao.addBook(book);

        // Get the book ID of the test book
        int bookId = book.getId();

        // Call the method to get the book by ID
        Book retrievedBook = bookDao.getBookById(bookId);

        // Convert expected and actual values to strings
        String expected = book.toString();
        String actual = retrievedBook.toString();

        Assertions.assertEquals(book, retrievedBook, "Retrieved book does not match the original book");
    }

    @Test
    public void testGetMostPopularBooks() {
        // Create test books
        Book book1 = new Book(33, "Book TEST 1", 1, 2023, "4565465465", 5);
        Book book2 = new Book(34, "Book TEST 2", 2, 2023, "ISBN323333456", 3);
        Book book3 = new Book(35, "Book TEST 3", 2, 2023, "77B345N789", 7);

        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);

        List<Book> popularBooks = bookDao.getMostPopularBooks(2);

        Assertions.assertEquals(2, popularBooks.size(), "Incorrect number of popular books");

        Assertions.assertEquals(book3, popularBooks.get(0), "First popular book does not match");
        Assertions.assertEquals(book1, popularBooks.get(1), "Second popular book does not match");
    }



}
