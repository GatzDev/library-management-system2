package library_management.test.transactionTest;

import library_management.entity.Author;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
import library_management.util.DatabaseManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class addTransactionTest {
    private TransactionDaoImpl transactionDao;
    private BookDaoImpl bookDao;
    private Book book;
    private UserDaoImpl userDao;
    private AuthorDaoImpl authorDao;

    @BeforeEach
    public void setup() {
        DatabaseManagerTest.connect();

        Connection connection = DatabaseManagerTest.getConnection();

        authorDao = new AuthorDaoImpl(connection);
        bookDao = new BookDaoImpl(connection);
        transactionDao = new TransactionDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
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
    public void testAddTransaction() {
        // Set up test data
        Author author = new Author("sfsfsfsdf", 1961);
        authorDao.addAuthor(author);
        int authorId = author.getId();


        User user = new User("dsgeg svddfdfdfdfd", "venci.dfjdjdj11111@example.com");
        userDao.addUser(user);
        int userId = user.getId();

        Book book = new Book("The Great dsfd", author, 1949, "99999999999", 1);
        bookDao.addBook(book);
        int bookId = book.getId();

        LocalDate borrowingDate = LocalDate.now();
        LocalDate returnDate = null;

        Transaction transaction = new Transaction(user, book, borrowingDate, returnDate);

        Transaction addedTransaction = transactionDao.addTransaction(transaction);

        assertNotNull(addedTransaction);

        // Assert that the added transaction has a valid ID
        assertTrue(addedTransaction.getId() > 0);

        Transaction retrievedTransaction = transactionDao.getTransactionById(addedTransaction.getId());
        assertNotNull(retrievedTransaction);
        assertNotNull(retrievedTransaction.getUser());
        assertNotNull(retrievedTransaction.getBook());
        assertEquals(userId, retrievedTransaction.getUser().getId());
        assertEquals(bookId, retrievedTransaction.getBook().getId());
        assertEquals(borrowingDate, retrievedTransaction.getBorrowingDate());
        assertNull(retrievedTransaction.getReturnDate());
    }
}

