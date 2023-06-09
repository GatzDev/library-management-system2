package library_management.impl;
import library_management.dao.AuthorDao;
import library_management.entity.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private Connection connection;
    private AuthorDao authorDao;

    public AuthorDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Author addAuthor(Author author) {
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
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int birthYear = rs.getInt("birth_year");

                Author author = new Author(name, birthYear);
                author.setId(id);
                authors.add(author);
            }

            rs.close();
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
                Author author = new Author( authorName, 0);
                author.setId(authorId);
                author.setBookCount(bookCount);
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve most prolific authors.");
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
                authors.add(author);
            }

            result.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }
}

