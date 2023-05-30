package library_management.impl;

import library_management.Dao.AuthorDao;
import library_management.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private Connection connection;

    public AuthorDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addAuthor(Author author) {
        try {
            String query = "INSERT INTO authors (name, birth_year) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBirthYear());
            statement.executeUpdate();
            System.out.println("Author added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add author.");
            e.printStackTrace();
        }
    }


    @Override
    public boolean updateAuthor(Author author) {
        try {
            String query = "UPDATE authors SET name = ?, birth_year = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBirthYear());
            statement.setInt(3, author.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean removeAuthor(int authorId) {
        String deleteAuthorQuery = "DELETE FROM authors WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteAuthorQuery)) {
            statement.setInt(1, authorId);
            int rowsAffected = statement.executeUpdate();
            return true;

//            if (rowsAffected > 0) {
//                System.out.println("Author with ID " + authorId + " has been removed successfully.");
//                return true;
//            } else {
//                System.out.println("Author with ID " + authorId + " does not exist.");
//                return false;
//            }

        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the author: " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            String query = "SELECT * FROM authors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("birth_year")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve authors.");
            e.printStackTrace();
        }
        return authors;
    }


    @Override
    public Author getAuthorById(int authorId) {
        try {
            String query = "SELECT * FROM authors WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, authorId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int birthYear = resultSet.getInt("birth_year");

                Author author = new Author(name, birthYear);
                author.setId(authorId);

                return author;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Author> getMostProlificAuthors(int limit) {
        List<Author> authors = new ArrayList<>();
        try {
            String query = "SELECT authors.id, authors.name, COUNT(books.author_id) AS book_count " +
                    "FROM authors " +
                    "JOIN books ON authors.id = books.author_id " +
                    "GROUP BY authors.id, authors.name " +
                    "ORDER BY book_count DESC " +
                    "LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int authorId = resultSet.getInt("id");
                String authorName = resultSet.getString("name");
                int bookCount = resultSet.getInt("book_count");
                Author author = new Author(authorId, authorName, 0);
                author.setBookCount(bookCount);
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve most prolific authors.");
            e.printStackTrace();
        }
        return authors;
    }


}

