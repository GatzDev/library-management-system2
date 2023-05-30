package library_management.impl;

import library_management.Dao.UserDao;
import library_management.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addUser(User user) {
        try {
            String query = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUser(User user) {
        try {
            String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getId());  // Set the third parameter for user ID
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deleteUser(int userId) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");

                User user = new User(id, name, email);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get users.");
            e.printStackTrace();
        }

        return users.stream().collect(Collectors.toList());
    }

    @Override
    public List<User> searchUsers(String keyword) {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users WHERE name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");

                User user = new User(id, name, email);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to search users.");
            e.printStackTrace();
        }

        return users;
    }



    @Override
    public User getUserById(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");

                return new User(id, name, email);
            } else {
                System.out.println("User not found with ID: " + userId);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user by ID: " + userId);
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public List<User> getMostActiveUsers(int limit) {
        List<User> activeUsers = new ArrayList<>();
        try {
            String query = "SELECT users.id, users.name, COUNT(transactions.user_id) AS transaction_count " +
                    "FROM users " +
                    "JOIN transactions ON users.id = transactions.user_id " +
                    "GROUP BY users.id, users.name " +
                    "ORDER BY transaction_count DESC " +
                    "LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                int transactionCount = resultSet.getInt("transaction_count");
                User user = new User(userId, userName);
                user.setTransactionCount(transactionCount);
                activeUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve most active users.");
            e.printStackTrace();
        }
        return activeUsers;
    }



}

