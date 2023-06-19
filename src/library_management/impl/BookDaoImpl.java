package library_management.impl;

import library_management.dao.AuthorDao;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.dao.BookDao;
import library_management.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final Connection connection;
    private final AuthorDao authorDao;

    public BookDaoImpl(Connection connection) {
        this.connection = connection;
        this.authorDao = new AuthorDaoImpl(connection);
    }

    @Override
    public boolean addBook(Book book) {
        try {
            String query = "INSERT INTO books (title, author_id, publication_year, isbn, stock) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sta = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            sta.setString(1, book.getTitle());

            Author author = book.getAuthor();
            if (author == null) {
                System.out.println("Cannot add a book without an author.");
                return false;
            }
            sta.setInt(2, book.getAuthor().getId());
            sta.setInt(3, book.getPublicationYear());

            String isbn = book.getISBN();
            if (!Constants.ISBN_PATTERN.matcher(isbn).matches()) {
                System.out.println("Invalid ISBN format.");
                return false;
            }

            sta.setString(4, book.getISBN());

            int stock = book.getStock();
            if (stock <= 0) {
                return false;
            }

            sta.setInt(5, book.getStock());
            int insertedRow = sta.executeUpdate();

            if (insertedRow > 0) {
                // Get t ID from the statement
                ResultSet generatedKeys = sta.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    book.setId(generatedId);
                    return true;
                }
            }

            return false;
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
            sta.setInt(2, book.getAuthor().getId());
            sta.setInt(3, book.getPublicationYear());

            String isbn = book.getISBN();
            if (!Constants.ISBN_PATTERN.matcher(isbn).matches()) {
                System.out.println("Invalid ISBN format.");
                return false;
            }

            sta.setString(4, book.getISBN());

            int stock = book.getStock();
            if (stock <= 0) {
                return false;
            }
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
    public List<Book> searchBooks(String keyword, AuthorDao authorDao) {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books " +
                    "WHERE title LIKE ? OR author_id IN " +
                    "(SELECT id FROM authors WHERE name LIKE ?) OR isbn LIKE ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, "%" + keyword + "%");
            sta.setString(2, "%" + keyword + "%");
            sta.setString(3, "%" + keyword + "%");
            ResultSet result = sta.executeQuery();
            while (result.next()) {
                int authorId = result.getInt("author_id");
                Author author = authorDao.getAuthorById(authorId);

                Book book = new Book(
                        result.getString("title"),
                        author,
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
            String query = "SELECT books.id, books.title, books.author_id, books.publication_year, books.isbn, books.stock, " +
                    "authors.name, authors.birth_year " +
                    "FROM books " +
                    "JOIN authors ON books.author_id = authors.id";
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);
            while (result.next()) {
                int bookId = result.getInt("id");
                String title = result.getString("title");
                int publicationYear = result.getInt("publication_year");
                String isbn = result.getString("isbn");
                int stock = result.getInt("stock");
                String authorName = result.getString("name");
                int authorBirthYear = result.getInt("birth_year");

                Author author = new Author(authorName, authorBirthYear);
                Book book = new Book(title, author, publicationYear, isbn, stock);
                book.setId(bookId);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get books.");
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        Book book = null;
        try {
            String query = "SELECT * FROM books WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, bookId);
            ResultSet result = sta.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                int authorId = result.getInt("author_id");
                int publicationYear = result.getInt("publication_year");
                String isbn = result.getString("isbn");
                int stock = result.getInt("stock");

                Author author = authorDao.getAuthorById(authorId);
                book = new Book(id, title, author, publicationYear, isbn, stock);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get book by ID." + bookId);
            e.printStackTrace();
        }
        return book;
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

                Author author = authorDao.getAuthorById(authorId);

                Book book = new Book(id, title, author, publicationYear, isbn, stock);
                availableBooks.add(book);
            }

            sta.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve available books.");
            e.printStackTrace();
        }
        return availableBooks;
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
                Book book = new Book(bookTitle);
                book.setId(bookId);

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

