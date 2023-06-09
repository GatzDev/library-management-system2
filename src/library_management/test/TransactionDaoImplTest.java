//package library_management.test;
//
//import library_management.dao.BookDao;
//import library_management.dao.TransactionDao;
//import library_management.dao.UserDao;
//import library_management.entity.Book;
//import library_management.entity.Transaction;
//import library_management.entity.User;
//import library_management.impl.BookDaoImpl;
//import library_management.impl.TransactionDaoImpl;
//import library_management.impl.UserDaoImpl;
//import library_management.util.Constants;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TransactionDaoImplTest {
//    private Connection connection;
//    private TransactionDao transactionDao;
//    private UserDao userDao;
//    private BookDao bookDao;
//
//
//    @BeforeEach
//    public void setup() throws SQLException {
//        // Set up the database connection
//        connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
//        transactionDao = new TransactionDaoImpl(connection);
//        userDao = new UserDaoImpl(connection);
//        bookDao = new BookDaoImpl(connection);
//    }
//
//    @Test
//    public void testAddTransaction() throws SQLException {
//        clearTransactions();
//
//        // Arrange
//        User user = new User("Vergo Sabastian", "subi99@gmail.com");
//        userDao.addUser(user);
//
//        Book book = new Book("Test Book", 2, 2020, "3456534545", 5);
//        bookDao.addBook(book);
//
//        LocalDate borrowingDate = LocalDate.now();
//        LocalDate returnDate = borrowingDate.plusDays(4);
//
//        Transaction transaction = new Transaction(borrowingDate, returnDate);
//
//        // Act
//        transactionDao.addTransaction(transaction);
//
//        // Assert
//        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM transactions WHERE id = ?")) {
//            stmt.setInt(1, transaction.getId());
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                assertTrue(rs.next(), "Transaction should be found");
//                Assertions.assertEquals(user.getId(), transaction.getUser().getId(), rs.getInt("user_id"));
//                Assertions.assertEquals(book.getId(), transaction.getBook().getId(), rs.getInt("book_id"));
//                Assertions.assertEquals(Date.valueOf(borrowingDate), rs.getDate("borrowing_date"));
//                Assertions.assertEquals(Date.valueOf(returnDate), rs.getDate("return_date"));
//            }
//        }
//    }
//
//    @Test
//    public void testGetAllTransactions() throws SQLException {
//        // Arrange
//        clearTransactions();
//
//        User user1 = new User( "Vergo Sabastian1", "subi992344@gmail.com");
//        userDao.addUser(user1);
//
//        User user2 = new User( "Vergo Sabastian2", "subi99@gmail.com");
//        userDao.addUser(user2);
//
//        Book book1 = new Book( "Test Book1", 5, 2001, "999999", 2);
//        bookDao.addBook(book1);
//
//        Book book2 = new Book( "Test Book2", 2, 2009, "6666666", 1);
//        bookDao.addBook(book2);
//
//        LocalDate borrowingDate1 = LocalDate.of(2023, 2, 13);
//        LocalDate returnDate1 = LocalDate.of(2022, 6, 18);
//
//        Transaction transaction1 = new Transaction( borrowingDate1, returnDate1);
//        Transaction addedTransaction1 = transactionDao.addTransaction(transaction1);
//
//        LocalDate borrowingDate2 = LocalDate.of(2021, 1, 11);
//        LocalDate returnDate2 = LocalDate.of(2020, 3, 13);
//
//       // Transaction transaction2 = new Transaction(user2.getId(), book2.getId(), borrowingDate2, returnDate2);
//      //  transactionDao.addTransaction(transaction2);
//
//        // Act
//        List<Transaction> transactions = transactionDao.getAllTransactions();
//
//        // Assert
//        assertEquals(2, transactions.size());
//        assertEquals(transactions.get(0),(addedTransaction1));
//        //assertTrue(transactions.contains(transaction2));
//
//    }
//
//    @Test
//    public void testGetTransactionById() throws SQLException {
//        // Arrange
//
//        clearTransactions();
//
//        User user = new User( "Vergo Sabastian", "subi99@gmail.com");
//        userDao.addUser(user);
//
//        Book book = new Book( "Test Book", 2, 2020, "3456534545", 5);
//        bookDao.addBook(book);
//
//        LocalDate borrowingDate = LocalDate.of(2023, 5, 30);
//        LocalDate returnDate = LocalDate.of(2023, 6, 28);
//        //Transaction transaction = new Transaction(user.getId(), book.getId(), borrowingDate, returnDate);
//        //transactionDao.addTransaction(transaction);
//
//        // Act
//        //Transaction retrievedTransaction = transactionDao.getTransactionById(transaction.getId());
//
//        // Assert
//        //assertNotNull(retrievedTransaction);
//        //assertEquals(transaction.getId(), retrievedTransaction.getId());
//        //assertEquals(transaction.getUserId(), retrievedTransaction.getUserId());
//        //assertEquals(transaction.getBookId(), retrievedTransaction.getBookId());
//        //assertEquals(transaction.getBorrowingDate(), retrievedTransaction.getBorrowingDate());
//        //assertEquals(transaction.getReturnDate(), retrievedTransaction.getReturnDate());
//    }
//
//    private void clearTransactions() throws SQLException {
//        String query = "DELETE FROM transactions";
//        Statement statement = connection.createStatement();
//        statement.executeUpdate(query);
//    }
//}
//
