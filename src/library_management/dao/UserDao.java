package library_management.dao;

import library_management.entity.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    User getUserById(int userId);
    List<User> getAllUsers();
    List<User> searchUsers(String keyword);
    public List<User> getMostActiveUsers(int limit);
    boolean userAddedToDatabase(User user);
    }
