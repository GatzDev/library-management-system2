//package library_management.impl;
//
//import library_management.DAO.UserDao;
//import library_management.entity.User;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserDaoImpl implements UserDao {
//    private Connection connection;
//
//    public UserDaoImpl(Connection connection) {
//        this.connection = connection;
//    }
//
//
//    @Override
//    public void addUser(User user) {
//        try {
//            String query = "INSERT INTO users (name, email) VALUES (?, ?)";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getEmail());
//            statement.executeUpdate();
//            System.out.println("User added successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to add user.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void updateUser(User user) {
//        try {
//            String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getEmail());
//            statement.executeUpdate();
//            System.out.println("User updated successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to update user.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void removeUser(int userId) {
//        try {
//            String query = "DELETE FROM users WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, userId);
//            statement.executeUpdate();
//            System.out.println("User removed successfully.");
//        } catch (SQLException e) {
//            System.out.println("Failed to remove user.");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public List<User> getAllUsers() {
//        List<User> users = new ArrayList<>();
//        try {
//            String query = "SELECT * FROM users";
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery(query);
//
//            while (result.next()) {
//                int userId = result.getInt("id");
//                String name = result.getString("name");
//                String email = result.getString("email");
//
//                User user = new User(userId, name, email);
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed to get users.");
//            e.printStackTrace();
//        }
//
//        return users.stream().collect(Collectors.toList());
//    }
//
//    @Override
//    public User getUserById(int userId) {
//        try {
//            String query = "SELECT * FROM users WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, userId);
//            ResultSet result = statement.executeQuery();
//
//            if (result.next()) {
//                int id = result.getInt("id");
//                String name = result.getString("name");
//                String email = result.getString("email");
//
//                return new User(id, name, email);
//            } else {
//                throw new IllegalArgumentException("User not found with ID: " + userId);
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed to get user by ID: " + userId);
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//}
//
