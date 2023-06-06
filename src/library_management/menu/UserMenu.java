package library_management.menu;
import library_management.dao.UserDao;
import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import library_management.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static library_management.util.Input.readIntInput;

public class UserMenu {
    private BufferedReader reader;
    private UserDao userDao;

    public UserMenu() {
        reader = new BufferedReader(new InputStreamReader(System.in));


        // Create a database connection
        try {
            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
            userDao = new UserDaoImpl(connection);
        } catch (
                SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();

        }
    }

    public void uMenu() {
        System.out.println("--- User Menu ---");
        System.out.println("1. Add User");
        System.out.println("2. Update User");
        System.out.println("3. Remove User");
        System.out.println("4. Search User");
        System.out.print("Enter your choice: ");

        int choice = readIntInput(reader);

        switch (choice) {
            case 1:
                addUser();
                break;
            case 2:
                updateUser();
                break;
            case 3:
                removeUser();
                break;
            case 4:
                searchUsers();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addUser() {
        try {
            System.out.println("Enter the name of the user:");
            String name = reader.readLine();

            System.out.println("Enter the email of the user:");
            String email = reader.readLine();

            User user = new User(name, email);

            userDao.addUser(user);
            System.out.println("User added successfully!");
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

    private void updateUser() {
        try {
            System.out.println("Enter the ID of the user to update:");
            int userId = Integer.parseInt(reader.readLine());

            // Retrieve the existing user by ID
            User user = userDao.getUserById(userId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.println("Enter the new name of the user (or leave blank to keep the existing name):");
            String newName = reader.readLine();

            if (!newName.isEmpty()) {
                user.setName(newName);
            }

            System.out.println("Enter the new email of the user (or leave blank to keep the existing email):");
            String newEmail = reader.readLine();

            if (!newEmail.isEmpty()) {
                user.setEmail(newEmail);
            }

            userDao.updateUser(user);
            System.out.println("User updated successfully!");
        } catch (
                IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

    private void removeUser() {
        try {
            System.out.println("Enter the ID of the user to remove:");
            int userId = Integer.parseInt(reader.readLine());

            // Retrieve the existing user by ID
            User user = userDao.getUserById(userId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            userDao.deleteUser(userId);
            System.out.println("User removed successfully!");
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

    private void searchUsers() {
        try {
            System.out.println("Enter the keyword to search for users:");
            String keyword = reader.readLine();

            List<User> users = userDao.searchUsers(keyword);

            if (users.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("Search results:");
                for (User user : users) {
                    System.out.println("ID: " + user.getId());
                    System.out.println("Name: " + user.getName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }
}

