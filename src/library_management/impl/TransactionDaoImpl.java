package library_management.impl;
import library_management.dao.BookDao;
import library_management.dao.TransactionDao;
import library_management.dao.UserDao;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private Connection connection;

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addTransaction(Transaction transaction) {
        try {
            String query = "INSERT INTO transactions (user_id, book_id, borrowing_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement sta = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            sta.setInt(1, transaction.getUserId());
            sta.setInt(2, transaction.getBookId());
            sta.setDate(3, Date.valueOf(transaction.getBorrowingDate()));
            sta.setDate(4, Date.valueOf(transaction.getReturnDate()));
            sta.executeUpdate();

            ResultSet generatedKeys = sta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int transactionId = generatedKeys.getInt(1);
                transaction.setId(transactionId);
                System.out.println("Transaction added successfully. Generated ID: " + transactionId);
            } else {
                System.out.println("Failed to add transaction.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to add transaction.");
            e.printStackTrace();
        }
    }

    @Override
    public Transaction getTransactionByUserAndBook(int userId, int bookId) {
        return null;
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        try {
            String query = "SELECT * FROM transactions WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, transactionId);

            ResultSet result = sta.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                int userId = result.getInt("user_id");
                int bookId = result.getInt("book_id");
                LocalDate borrowingDate = result.getDate("borrowing_date").toLocalDate();
                LocalDate returnDate = result.getDate("return_date") != null
                        ? result.getDate("return_date").toLocalDate() : null;

                return new Transaction(id, userId, bookId, borrowingDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions(UserDao userDao, BookDao bookDao) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String query = "SELECT * FROM transactions";
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);
            while (result.next()) {
                int transactionId = result.getInt("id");
                int userId = result.getInt("user_id");
                int bookId = result.getInt("book_id");
                Date borrowingDate = result.getDate("borrowing_date");
                Date returnDate = result.getDate("return_date");

                User user = userDao.getUserById(userId);
                Book book = bookDao.getBookById(bookId);

                Transaction transaction = new Transaction(transactionId, user, book, borrowingDate.toLocalDate(), returnDate.toLocalDate());
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve transactions.");
            e.printStackTrace();
        }
        return new ArrayList<>(transactions);
    }


    @Override
    public void updateTransaction(Transaction transaction) {
        try {
            String query = "UPDATE transactions SET user_id = ?, book_id = ?, borrowing_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, transaction.getUser().getId());
            sta.setInt(2, transaction.getBook().getId());
            sta.setDate(3, Date.valueOf(transaction.getBorrowingDate()));
            sta.setDate(4, Date.valueOf(transaction.getReturnDate()));
            sta.setInt(5, transaction.getId());
            sta.executeUpdate();
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, transactionId);
            sta.executeUpdate();
            System.out.println("Transaction removed successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to remove transaction.");
            e.printStackTrace();
        }
    }
}
