package library_management.impl;
import library_management.dao.TransactionDao;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private Connection connection;
    private UserDaoImpl userDaoImpl;
    private BookDaoImpl bookDaoImpl;
    private Transaction[] transactions
            ;
    private BookDaoImpl bookDao;

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
        this.userDaoImpl = new UserDaoImpl(connection);
        this.bookDaoImpl = new BookDaoImpl(connection);
    }


public Transaction addTransaction(Transaction transaction) {
        try {
            User user = transaction.getUser(); // Get the User object from the Transaction
            if (user == null) {
                System.out.println("User object is null. Please set a valid User object.");
                return null;
            }

            String query = "INSERT INTO transactions (user_id, book_id, borrowing_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement sta = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            sta.setInt(1, user.getId()); // Use the User object to retrieve the ID
            sta.setInt(2, transaction.getBook().getId());
            sta.setDate(3, Date.valueOf(transaction.getBorrowingDate()));
            sta.setDate(4, null);
            sta.executeUpdate();

            ResultSet generatedKeys = sta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int transactionId = generatedKeys.getInt(1);
                transaction.setId(transactionId);
                System.out.println("Transaction added successfully. Generated ID: " + transactionId);
                return transaction;
            } else {
                System.out.println("Failed to add transaction.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to add transaction.");
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Transaction getTransactionByUserAndBook(int userId, int bookId) {
        try {
            String query = "SELECT * FROM transactions WHERE user_id = ? AND book_id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, userId);
            sta.setInt(2, bookId);

            ResultSet result = sta.executeQuery();
            if (result.next()) {
                int transactionId = result.getInt("id");
                LocalDate borrowingDate = result.getObject("borrowing_date", LocalDate.class);
                LocalDate returnDate = result.getObject("return_date", LocalDate.class);


                User user = userDaoImpl.getUserById(userId);
                Book book = bookDaoImpl.getBookById(bookId);

                return new Transaction(transactionId, user, book, borrowingDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

                return new Transaction( borrowingDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBorrowedBooksByUser(int userId) {
        List<Book> borrowedBooks = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM transactions WHERE user_id = ? AND return_date IS NULL";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                Book book = bookDaoImpl.getBookById(bookId);
                borrowedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return borrowedBooks;
    }








    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String query = "SELECT * FROM transactions";
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);
            while (result.next()) {
                int userId = result.getInt("user_id");
                int bookId = result.getInt("book_id");
                Date borrowingDate = result.getDate("borrowing_date");
                Date returnDate = result.getDate("return_date");

                User user = userDaoImpl.getUserById(userId);
                Book book = bookDaoImpl.getBookById(bookId);

                Transaction transaction = new Transaction(user, book, borrowingDate.toLocalDate(), returnDate.toLocalDate());
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve transactions.");
            e.printStackTrace();
        }
        return transactions;
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
