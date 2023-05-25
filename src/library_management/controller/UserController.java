package library_management.controller;

import library_management.model.User;

import java.util.List;

public interface UserController {
    void addUser(User user);
    void updateUser(User user);
    void removeUser(int userId);
    User getUserById(int userId);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> searchUsers(String keyword);

}
