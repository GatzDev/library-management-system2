package library_management.impl;

import library_management.dao.AuthorDao;
import library_management.entity.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private final Connection connection;

    public AuthorDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Author addAuthor(Author author) {
        if (author == null || author.getName() == null || author.getName().isEmpty()) {
            System.out.println("Failed to add author.");
            return null;
        }

        int currentYear = LocalDate.now().getYear();
        if (author.getBirthYear() > currentYear || author.getBirthYear() < 0) {
            System.out.println("Invalid birth year. Failed to add author.");
            return null;
        }
        try {
            String query = "INSERT INTO authors (name, birth_year) VALUES (?, ?)";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, author.getName());
            sta.setInt(2, author.getBirthYear());
            sta.executeUpdate();
            System.out.println("Author added successfully.");
            return author;
        } catch (SQLException e) {
            System.out.println("Failed to add author.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAuthor(Author author) {
        try {
            String query = "UPDATE authors SET name = ?, birth_year = ? WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, author.getName());
            sta.setInt(2, author.getBirthYear());
            sta.setInt(3, author.getId());
            sta.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Author> getAllAuthorsWithId() {
        List<Author> authors = new ArrayList<>();

        try {
            String query = "SELECT id, name, birth_year FROM authors";
            PreparedStatement sta = connection.prepareStatement(query);
            ResultSet result = sta.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int birthYear = result.getInt("birth_year");

                Author author = new Author(name, birthYear);
                author.setId(id);
                authors.add(author);
            }

            result.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public boolean removeAuthor(int authorId) {
        String deleteAuthorQuery = "DELETE FROM authors WHERE id = ?";

        try (PreparedStatement sta = connection.prepareStatement(deleteAuthorQuery)) {
            sta.setInt(1, authorId);
            int rowsDeleted = sta.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error while deleting the author: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            String query = "SELECT id, name, birth_year FROM authors";
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int birthYear = result.getInt("birth_year");
                Author author = new Author(name, birthYear);
                author.setId(id);
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
        Author author = null;

        try {
            String query = "SELECT * FROM authors WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, authorId);

            ResultSet result = sta.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                int birthYear = result.getInt("birth_year");

                author = new Author(name, birthYear);
                author.setId(authorId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, limit);
            ResultSet result = sta.executeQuery();
            while (result.next()) {
                int authorId = result.getInt("id");
                String authorName = result.getString("name");
                int bookCount = result.getInt("book_count");
                Author author = new Author(authorName, 0);
                author.setId(authorId);
                author.setBookCount(bookCount);
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get most prolific authors.");
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> searchAuthor(String keyword) {
        List<Author> authors = new ArrayList<>();

        try {
            String query = "SELECT * FROM authors WHERE name LIKE ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, "%" + keyword + "%");
            ResultSet result = sta.executeQuery();

            while (result.next()) {
                int authorId = result.getInt("id");
                String name = result.getString("name");
                int birthYear = result.getInt("birth_year");

                Author author = new Author(name, birthYear);
                author.setId(authorId);
                authors.add(author);
            }

            result.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    public boolean authorExistsInDatabase(Author author) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM authors WHERE name = ? AND birth_year = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBirthYear());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);
                exists = count > 0;
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}

