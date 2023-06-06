package library_management.impl;
import library_management.entity.Book;
import library_management.dao.BookDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private Connection connection;

    public BookDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean addBook(Book book) {
        try {
            String query = "INSERT INTO books (title, author_id, publication_year, isbn, stock) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setInt(3, book.getPublicationYear());
            statement.setString(4, book.getISBN());
            statement.setInt(5, book.getStock());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            String query = "UPDATE books SET title = ?, author_id = ?, publication_year = ?, " +
                    "isbn = ?, stock = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setInt(3, book.getPublicationYear());
            statement.setString(4, book.getISBN());
            statement.setInt(5, book.getStock());
            statement.setInt(6, book.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean removeBook(int bookId) {
        try {
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }


    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books WHERE title LIKE ? OR author_id IN (SELECT id FROM authors WHERE name LIKE ?) OR isbn LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("publication_year"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("stock")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Failed to search books.");
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("author_id"),
                        resultSet.getInt("publication_year"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("stock")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve books.");
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve the book by ID." + bookId + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();

        try {
            String query = "SELECT * FROM books WHERE stock > 0";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int publicationYear = resultSet.getInt("publication_year");
                String isbn = resultSet.getString("isbn");
                int stock = resultSet.getInt("stock");

                Book book = new Book(id, title, authorId, publicationYear, isbn, stock);
                availableBooks.add(book);
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve available books.");
            e.printStackTrace();
        }

        return availableBooks;
    }


    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int authorId = resultSet.getInt("author_id");
        int publicationYear = resultSet.getInt("publication_year");
        String isbn = resultSet.getString("isbn");
        int stock = resultSet.getInt("stock");
        return new Book(id, title, authorId, publicationYear, isbn, stock);
    }

    @Override
    public List<Book> getMostPopularBooks(int limit) {
        List<Book> popularBooks = new ArrayList<>();
        try {
            String query = "SELECT books.id, books.title, COUNT(transactions.book_id) AS transaction_count " +
                    "FROM books " +
                    "JOIN transactions ON books.id = transactions.book_id " +
                    "GROUP BY books.id, books.title " +
                    "ORDER BY transaction_count DESC " +
                    "LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String bookTitle = resultSet.getString("title");
                int transactionCount = resultSet.getInt("transaction_count");
                Book book = new Book(bookId, bookTitle);
                book.setTransactionCount(transactionCount);
                popularBooks.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve most popular books.");
            e.printStackTrace();
        }
        return popularBooks;
    }

    @Override
    public int getBooksByAuthorId(int authorId) {
        int bookCount = 0;

        try {
            String query = "SELECT COUNT(*) AS book_count FROM books WHERE author_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                bookCount = resultSet.getInt("book_count");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookCount;
    }






}

