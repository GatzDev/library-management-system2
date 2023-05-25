//package library_management.impl;
//
//import library_management.entity.Author;
//import library_management.DAO.AuthorDao;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AuthorDaoImpl implements AuthorDao {
//    private Connection connection;
//
//    public AuthorDaoImpl(Connection connection) {
//        this.connection = connection;
//    }
//
//
//    @Override
//    public void addAuthor(Author author) {
//        try {
//            String query = "INSERT INTO authors (name, birth_year) VALUES (?, ?)";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, author.getName());
//            statement.setInt(2, author.getBirthYear());
//            statement.executeUpdate();
//            System.out.println("Author added successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to add author.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void updateAuthor(Author author) {
//        try {
//            String query = "UPDATE authors SET name = ?, birth_year = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, author.getName());
//            statement.setInt(2, author.getBirthYear());
//            statement.setInt(3, author.getId());
//            statement.executeUpdate();
//            System.out.println("Author updated successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to update author.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void removeAuthor(int authorId) {
//        try {
//            String query = "DELETE FROM authors WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, authorId);
//            statement.executeUpdate();
//            System.out.println("Author removed successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to remove author.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public List<Author> getAllAuthors() {
//        List<Author> authors = new ArrayList<>();
//        try {
//            String query = "SELECT * FROM authors";
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                Author author = new Author(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getInt("birth_year")
//                );
//                authors.add(author);
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed to retrieve authors.");
//            e.printStackTrace();
//        }
//        return authors;
//    }
//}
//
