package library_management.Dao;

import library_management.entity.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void updateUser(User user);
    void removeUser(int userId);
    User getUserById(int userId);
    List<User> getAllUsers();
    List<User> searchUsers(String keyword);

}
