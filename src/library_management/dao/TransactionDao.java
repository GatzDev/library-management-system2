package library_management.dao;

import library_management.entity.Book;
import library_management.entity.Transaction;

import java.util.List;

public interface TransactionDao {
    Transaction addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void removeTransaction(int transactionId);
    Transaction getTransactionById(int transactionId);

    List<Book> getBorrowedBooksByUser(int userId);

    List<Transaction> getAllTransactions();
    Transaction getTransactionByUserAndBook(int userId, int bookId);

}
