package library_management.menu;

import library_management.dao.UserDao;
import library_management.entity.User;
import library_management.impl.UserDaoImpl;
import library_management.util.Constants;
import library_management.util.DatabaseManager;
import library_management.util.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

import static library_management.util.Constants.EMAIL_PATTERN;
import static library_management.util.Input.readIntInput;

public class UserMenu {
    private BufferedReader reader;
    private UserDao userDao;

    public UserMenu() {
        reader = new BufferedReader(new InputStreamReader(System.in));

        DatabaseManager.connect();

        Connection connection = DatabaseManager.getConnection();
        userDao = new UserDaoImpl(connection);
    }

    public void uMenu() {
        System.out.println("--- User Menu ---");
        System.out.println("1. Add User");
        System.out.println("2. Update User");
        System.out.println("3. Remove User");
        System.out.println("4. Search User");
        System.out.println("5. Return to Previous Menu");
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
            case 5:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    private void addUser() {
        try {
            System.out.println("Enter the name of the user:");
            String name = reader.readLine();

            boolean validName = false;

            while (!validName) {
                if (name == null || name.trim().isEmpty()) {
                    System.out.println("Invalid name. Please enter a valid name.");
                    name = reader.readLine();
                } else {
                    validName = true;
                    System.out.println("Enter the email of the user:");
                    String email = reader.readLine();

                    boolean validEmail = false;

                    while (!validEmail) {
                        Matcher matcher = EMAIL_PATTERN.matcher(email);
                        if (matcher.matches()) {
                            validEmail = true;
                            User user = new User(name, email);
                            userDao.addUser(user);
                            System.out.println("User added successfully!");
                        } else {
                            System.out.println("Invalid email format. Please enter a valid email.");
                            email = reader.readLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateUser() {
        List<User> users = userDao.getAllUsers();

        System.out.println("--- List of Users ---");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }

        try {
            System.out.println("Enter the ID of the user to update:");
            int userId = Integer.parseInt(reader.readLine());

            // TAke the existing user by ID
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
                boolean validEmail = false;

                while (!validEmail) {
                    Matcher matcher = EMAIL_PATTERN.matcher(newEmail);
                    if (matcher.matches()) {
                        validEmail = true;
                        user.setEmail(newEmail);
                    } else {
                        System.out.println("Invalid email format. Please enter a valid email.");
                        newEmail = reader.readLine();
                    }
                }
            }

            userDao.updateUser(user);
            System.out.println("User with ID: " + userId + " has been update successfully.");
        } catch (IOException e) {
            System.out.println("Failed to update the user");
            e.printStackTrace();
        }
    }

    private void removeUser() {
        List<User> users = userDao.getAllUsers();

        System.out.println("--- List of Users ---");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }

        System.out.println("Enter the ID of the user to remove:");
        int userId = Input.readIntInput(reader);

        User user = userDao.getUserById(userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        boolean removed = userDao.deleteUser(userId);
        if (removed) {
            System.out.println("User with ID: " + userId + " has been removed successfully.");
        } else {
            System.out.println("Failed to remove the user with ID: " + userId);
        }
    }

    private void searchUsers() {
        try {
            System.out.println("Enter the keyword:");
            String keyword = reader.readLine();

            if (keyword.isEmpty()) {
                System.out.println("Cannot be empty. Please enter a valid keyword.");
                keyword = reader.readLine();
            }

            List<User> users = userDao.searchUsers(keyword);

            if (users.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("--- Search results ---");
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

