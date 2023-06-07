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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, book.getTitle());
            sta.setInt(2, book.getAuthorId());
            sta.setInt(3, book.getPublicationYear());
            sta.setString(4, book.getISBN());
            sta.setInt(5, book.getStock());
            sta.executeUpdate();
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, book.getTitle());
            sta.setInt(2, book.getAuthorId());
            sta.setInt(3, book.getPublicationYear());
            sta.setString(4, book.getISBN());
            sta.setInt(5, book.getStock());
            sta.setInt(6, book.getId());
            int rowsUpdated = sta.executeUpdate();
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, bookId);
            int rowsDeleted = sta.executeUpdate();
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, "%" + keyword + "%");
            sta.setString(2, "%" + keyword + "%");
            sta.setString(3, "%" + keyword + "%");
            ResultSet result = sta.executeQuery();
            while (result.next()) {
                Book book = new Book(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("author_id"),
                        result.getInt("publication_year"),
                        result.getString("isbn"),
                        result.getInt("stock")
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
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);
            while (result.next()) {
                Book book = new Book(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("author_id"),
                        result.getInt("publication_year"),
                        result.getString("isbn"),
                        result.getInt("stock")
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
        try (PreparedStatement sta = connection.prepareStatement(query)) {
            sta.setInt(1, bookId);
            ResultSet result = sta.executeQuery();
            if (result.next()) {
                return extractBookFromResultSet(result);
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
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                int authorId = result.getInt("author_id");
                int publicationYear = result.getInt("publication_year");
                String isbn = result.getString("isbn");
                int stock = result.getInt("stock");

                Book book = new Book(id, title, authorId, publicationYear, isbn, stock);
                availableBooks.add(book);
            }

            sta.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve available books.");
            e.printStackTrace();
        }
        return availableBooks;
    }

    private Book extractBookFromResultSet(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String title = result.getString("title");
        int authorId = result.getInt("author_id");
        int publicationYear = result.getInt("publication_year");
        String isbn = result.getString("isbn");
        int stock = result.getInt("stock");
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, limit);
            ResultSet result = sta.executeQuery();
            while (result.next()) {
                int bookId = result.getInt("id");
                String bookTitle = result.getString("title");
                int transactionCount = result.getInt("transaction_count");
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, authorId);
            ResultSet result = sta.executeQuery();

            if (result.next()) {
                bookCount = result.getInt("book_count");
            }

            result.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookCount;
    }
}

