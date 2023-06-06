package library_management.test;
import library_management.dao.TransactionDao;
import library_management.dao.UserDao;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
import library_management.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDaoImplTest {
    private Connection connection;
    private TransactionDao transactionDao;


    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
        transactionDao = new TransactionDaoImpl(connection);


    }

    @Test
    public void testGetAllTransactions() {
        LocalDate borrowingDate = LocalDate.now();
        LocalDate returnDate = borrowingDate.plusDays(4);
        // Arrange
        Transaction transaction1 = new Transaction(3, 10, 29, borrowingDate, returnDate);
        Transaction transaction2 = new Transaction(4, 11, 18, borrowingDate, returnDate);
        // Act
        List<Transaction> allTransactions = transactionDao.getAllTransactions();
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction);
            // Assert
            assertEquals(2, allTransactions.size());
            assertTrue(allTransactions.contains(transaction1));
            assertTrue(allTransactions.contains(transaction2));


        }
    }

}

