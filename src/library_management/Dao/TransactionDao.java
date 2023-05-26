package library_management.Dao;

import library_management.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionDao {
    void addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void removeTransaction(int transactionId);
    Transaction getTransactionById(int transactionId);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByUser(int userId);
    List<Transaction> getTransactionsByBook(int bookId);
    List<Transaction> getTransactionsByDate(LocalDate date);
}
