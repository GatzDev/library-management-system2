package library_management.controllerImpl;

import library_management.model.Book;
import library_management.controller.BookController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookControllerImpl implements BookController {
    private Connection connection;

    public BookControllerImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void addBook(Book book) {
        try {
            String query = "INSERT INTO books (title, author, publication_year, isbn, availability) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublicationYear());
            statement.setString(4, book.getISBN());
            statement.setBoolean(5, book.isAvailable());
            statement.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add book.");
            e.printStackTrace();

        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            String query = "UPDATE books SET title = ?, author = ?, publication_year = ?, " +
                    "isbn = ?, availability = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublicationYear());
            statement.setString(4, book.getISBN());
            statement.setBoolean(5, book.isAvailable());
            statement.setInt(6, book.getBookId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update book.");
            e.printStackTrace();
        }
    }


    @Override
    public void removeBook(int bookId) {
        try {
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to remove book.");
            e.printStackTrace();
        }
    }


    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("publication_year"),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("availability")
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
                        resultSet.getString("author"),
                        resultSet.getInt("publication_year"),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("availability")
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
        try {
            String query = "SELECT * FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int publicationYear = resultSet.getInt("publication_year");
                String isbn = resultSet.getString("isbn");
                boolean availability = resultSet.getBoolean("availability");

                return new Book(id, title, author, publicationYear, isbn, availability);
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch book by ID: " + bookId);
            e.printStackTrace();
        }

        return null;
    }

}

