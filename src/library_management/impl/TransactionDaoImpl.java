package library_management.impl;

import library_management.Dao.BookDao;
import library_management.Dao.TransactionDao;
import library_management.Dao.UserDao;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDaoImpl implements TransactionDao {
    private Connection connection;

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addTransaction(Transaction transaction) {
        try {
            String query = "INSERT INTO transactions (user_id, book_id, borrowing_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transaction.getUser().getId());
            statement.setInt(2, transaction.getBook().getId());
            statement.setDate(3, Date.valueOf(transaction.getBorrowingDate()));
            statement.setDate(4, Date.valueOf(transaction.getReturnDate()));
            statement.executeUpdate();
            System.out.println("Transaction added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add transaction.");
            e.printStackTrace();
        }
    }


    @Override
    public void updateTransaction(Transaction transaction) {
        try {
            String query = "UPDATE transactions SET user_id = ?, book_id = ?, borrowing_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transaction.getUser().getId());
            statement.setInt(2, transaction.getBook().getId());
            statement.setDate(3, Date.valueOf(transaction.getBorrowingDate()));
            statement.setDate(4, Date.valueOf(transaction.getReturnDate()));
            statement.setInt(5, transaction.getId());
            statement.executeUpdate();
            System.out.println("Transaction updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update transaction.");
            e.printStackTrace();
        }
    }


    @Override
    public void removeTransaction(int transactionId) {
        try {
            String query = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);
            statement.executeUpdate();
            System.out.println("Transaction removed successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to remove transaction.");
            e.printStackTrace();
        }
    }



    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String query = "SELECT * FROM transactions";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                Date borrowingDate = resultSet.getDate("borrowing_date");
                Date returnDate = resultSet.getDate("return_date");

                // Retrieve the corresponding user and book objects
                UserDao userDao = new UserDaoImpl(connection);
                User user = userDao.getUserById(userId);
                BookDao bookDao = new BookDaoImpl(connection);
                Book book = bookDao.getBookById(bookId);

                Transaction transaction = new Transaction(transactionId, user, book, borrowingDate.toLocalDate(), returnDate.toLocalDate());
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve transactions.");
            e.printStackTrace();
        }
        return  transactions.stream().collect(Collectors.toList());
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        try {
            String query = "SELECT * FROM transactions WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                LocalDate borrowingDate = resultSet.getDate("borrowing_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date") != null
                        ? resultSet.getDate("return_date").toLocalDate() : null;

                // Create and return the Transaction object
                return new Transaction(id, userId, bookId, borrowingDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Transaction with the specified ID not found
    }

    @Override
    public List<Transaction> getTransactionsByUser(int userId) {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByBook(int bookId) {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        return null;
    }

}
