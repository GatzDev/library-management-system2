package library_management.test;

import library_management.dao.BookDao;
import library_management.entity.Book;
import library_management.impl.BookDaoImpl;
import library_management.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDaoImplTest {
    private Connection connection;
    private BookDao bookDao;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

        // Create an instance of the BookDao implementation
        bookDao = new BookDaoImpl(connection);
    }

    @Test
    public void testAddBook() {
        Book book = new Book(11, "title11", 1, 2013, "isbn43332", 1);
        assertTrue(bookDao.addBook(book));
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(60, "title", 1, 2023, "isbn", 1);
        bookDao.addBook(book);
        book.setTitle("new title");
        assertTrue(bookDao.updateBook(book));
    }

    @Test
    public void testRemoveBook() {
        // Arrange
        Book book = new Book(62, "tdfdf", 2, 2001, "ISBN4565654", 1);
        int bookId = 62; // assign a book id
        bookDao.addBook(book);

        // Act
        boolean result = bookDao.removeBook(bookId);

        // Assert
        assertTrue(result);
        assertNull(bookDao.getBookById(bookId));
    }

    @Test
    public void testSearchBooks() {
        // Arrange
        String keyword = "bob4"; // keyword to search for
        Book book1 = new Book(70, "bob401", 1, 2022, "ISBN123", 2);
        Book book2 = new Book(71, "bob402", 2, 2021, "ISBN456", 1);
        bookDao.addBook(book1);
        bookDao.addBook(book2);

        // Act
        List<Book> searchResult = bookDao.searchBooks(keyword);

        // Assert
        assertEquals(2, searchResult.size());
        assertTrue(searchResult.contains(book1));
        assertTrue(searchResult.contains(book2));
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        Book book1 = new Book(1, "Book to get 1", 1, 2022, "ISBN123", 1);
        Book book2 = new Book(2, "Book to get 2", 2, 2021, "ISBN456", 2);
        bookDao.addBook(book1);
        bookDao.addBook(book2);

        // Act
        List<Book> allBooks = bookDao.getAllBooks();

        // Assert
        assertEquals(2, allBooks.size());
        assertTrue(allBooks.contains(book1)); //
        assertTrue(allBooks.contains(book2));
    }

    @Test
    public void testGetBookById() {
        // Arrange
        Book book = new Book(75, "Book by Id", 2, 2012, "ISBN1323223", 1);
        bookDao.addBook(book); // add the book to the database

        // Act
        Book retrievedBook = bookDao.getBookById(75);

        // Assert
        assertNotNull(retrievedBook);
        assertEquals(book.getId(), retrievedBook.getId());
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getAuthorId(), retrievedBook.getAuthorId());
        assertEquals(book.getPublicationYear(), retrievedBook.getPublicationYear());
        assertEquals(book.getISBN(), retrievedBook.getISBN());
        assertEquals(book.getStock(), retrievedBook.getStock());
    }

    @Test
    public void testGetAvailableBooks() {
        // Arrange
        Book book1 = new Book(1, "Book Get Available Book 1", 1, 2012, "ISBN123343", 1);
        Book book2 = new Book(2, "Book Get Available Book 2", 2, 2013, "ISBN45634", 2);
        Book book3 = new Book(3, "Book Get Available Book 3", 1, 2011, "ISBN7893454", 0);
        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);

        // Act
        List<Book> availableBooks = bookDao.getAvailableBooks();

        // Assert
        assertEquals(2, availableBooks.size());

        // Check the properties of the available books
        for (Book book : availableBooks) {
            assertTrue(book.getStock() > 0);
        }
    }

    @Test
    public void testGetBooksByAuthorId() {
        // Arrange
        int authorId = 6;
        Book book1 = new Book(1, "Book By Author Id 1", authorId, 2012, "456546546", 1);
        Book book2 = new Book(2, "Book By Author Id 2", authorId, 2013, "45645654456", 3);
        Book book3 = new Book(3, "Book By Author Id 3", 2, 2011, "45645456450", 2);
        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);

        // Act
        int bookCount = bookDao.getBooksByAuthorId(authorId);

        // Assert
        assertEquals(2, bookCount);
    }

    @Test
    public void testGetMostPopularBooks() {
        // Arrange
        int limit = 1;
        Book book1 = new Book(85, "Book Most Popular Books 1", 1, 2011, "1231232", 1);
        Book book2 = new Book(86, "Book Most Popular Books 2", 2, 2011, "789789", 1);
        Book book3 = new Book(87, "Book Most Popular Books 3", 5, 2011, "45460525", 1);
        book1.setTransactionCount(11);
        book2.setTransactionCount(9);
        book3.setTransactionCount(8);
        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);

        // Act
        List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

        // Assert
        assertEquals(limit, popularBooks.size());
        assertEquals(book1.getTitle(), popularBooks.get(0).getTitle()); // check the order of books based on transaction count
        assertEquals(book3.getTitle(), popularBooks.get(1).getTitle());
        assertEquals(book2.getTitle(), popularBooks.get(2).getTitle());
    }
}




