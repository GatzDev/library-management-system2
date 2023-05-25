package library_management.controller;

import library_management.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionController {
    void addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void removeTransaction(int transactionId);
    Transaction getTransactionById(int transactionId);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByUser(int userId);
    List<Transaction> getTransactionsByBook(int bookId);
    List<Transaction> getTransactionsByDate(LocalDate date);
}
