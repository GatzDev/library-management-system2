package library_management.impl;
import library_management.dao.UserDao;
import library_management.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {
    private final Connection connection;
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(User user) {
        try {
            String query = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, user.getName());
            sta.setString(2, user.getEmail());
            sta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, user.getName());
            sta.setString(2, user.getEmail());
            sta.setInt(3, user.getId());
            sta.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, userId);
            int rowsDeleted = sta.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");
                User user = new User(name, email);
                user.setId(id);
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, "%" + keyword + "%");
            ResultSet result = sta.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String email = result.getString("email");

                User user = new User(name, email);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to search users : " + keyword + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, userId);
            ResultSet result = sta.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");

                final User user = new User(name, email);
                user.setId(id);

                return user;
            } else {
                System.out.println("User not found with ID: " + userId);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user by ID: " + userId + ". Error: " + e.getMessage());
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
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setInt(1, limit);
            ResultSet result = sta.executeQuery();
            while (result.next()) {
                int userId = result.getInt("id");
                String userName = result.getString("name");
                int transactionCount = result.getInt("transaction_count");
                User user = new User(userName);
                user.setTransactionCount(transactionCount);
                user.setId(userId);
                activeUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve most active users.");
            e.printStackTrace();
        }
        return activeUsers;
    }

     public boolean userAddedToDatabase(User user) {
        try {
            // Execute a query to check if the user exists in the database
            String query = "SELECT COUNT(*) FROM users WHERE name = ? AND email = ?";
            PreparedStatement sta = connection.prepareStatement(query);
            sta.setString(1, user.getName());
            sta.setString(2, user.getEmail());
            ResultSet resultSet = sta.executeQuery();

            // Check the result of the query
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

