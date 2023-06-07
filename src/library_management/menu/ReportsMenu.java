package library_management.menu;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.dao.TransactionDao;
import library_management.dao.UserDao;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
import library_management.util.Constants;
import library_management.util.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class ReportsMenu {
    private BufferedReader reader;
    private AuthorDao authorDao;
    private BookDao bookDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    public ReportsMenu() {
        reader = new BufferedReader(new InputStreamReader(System.in));

        try {

            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

            bookDao = new BookDaoImpl(connection);
            authorDao = new AuthorDaoImpl(connection);
            userDao = new UserDaoImpl(connection);
            transactionDao = new TransactionDaoImpl(connection);

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }

    public void generateReports() {
        System.out.println("Select the type of report to generate:");
        System.out.println("1. All Authors");
        System.out.println("2. All Books");
        System.out.println("3. All Users");
        System.out.println("4. All Transactions");
        System.out.println("5. Most Prolific Authors");
        System.out.println("6. Most Popular Books");
        System.out.println("7. Most Active Users");
        System.out.println("8. Return to Previous Menu");
        System.out.print("\nEnter your choice: ");

        int choice = Input.readIntInput(reader);

        switch (choice) {
            case 1:
                getAllAuthors();
                break;
            case 2:
                getAllBooks();
                break;
            case 3:
                getAllUsers();
                break;
            case 4:
                getAllTransactions();
                break;
            case 5:
                getMostProlificAuthors();
                break;
            case 6:
                getMostPopularBooks();
                break;
            case 7:
                mostActiveUsersReport();
                break;
            case 8:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void getAllAuthors() {
        List<Author> authors = authorDao.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No authors found.");
        } else {
            System.out.println("All Authors:");
            for (Author author : authors) {
                System.out.println("ID: " + author.getId());
                System.out.println("Name: " + author.getName());
                System.out.println("Birth Year: " + author.getBirthYear());
                System.out.println();
            }
        }
    }

    private void getAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nAll Books:");
            for (Book book : books) {
                System.out.println("ID: " + book.getId());
                System.out.println("Title: " + book.getTitle());
                //????   Retrieve the author for the book using the author ID
                Author author = authorDao.getAuthorById(book.getAuthorId());
                //????   Set the author name for the book
                book.setAuthor(author.getName());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Publication Year: " + book.getPublicationYear());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println();
            }
        }
    }

    private void getAllUsers() {
        List<User> users = userDao.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Users:");
            for (User user : users) {
                System.out.println("ID: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println();
            }
        }
    }

    private void getAllTransactions() {
        List<Transaction> transactions = transactionDao.getAllTransactions(userDao, bookDao);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transactions:");
            for (Transaction transaction : transactions) {
                System.out.println("ID: " + transaction.getId());
                System.out.println("User ID: " + transaction.getUser().getId());
                System.out.println("Book ID: " + transaction.getBook().getId());
                System.out.println("Return Date: " + transaction.getReturnDate());
                System.out.println();
            }
        }
    }

    private void getMostProlificAuthors() {
        System.out.println("Enter the limit for the number of authors:");
        try {
            int limit = Integer.parseInt(reader.readLine());

            List<Author> authors = authorDao.getMostProlificAuthors(limit);

            if (authors.isEmpty()) {
                System.out.println("No authors found.");
            } else {
                System.out.println("Most Prolific Authors:");
                for (Author author : authors) {
                    System.out.println("Author ID: " + author.getId());
                    System.out.println("Author Name: " + author.getName());
                    System.out.println("Book Count: " + author.getBookCount());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

    private void getMostPopularBooks() {
        System.out.println("Enter the limit for the number of books:");
        try {
            int limit = Integer.parseInt(reader.readLine());

            List<Book> popularBooks = bookDao.getMostPopularBooks(limit);

            if (popularBooks.isEmpty()) {
                System.out.println("No popular books found.");
            } else {
                System.out.println("Most Popular Books:");
                for (Book book : popularBooks) {
                    System.out.println("Book ID: " + book.getId());
                    System.out.println("Book Title: " + book.getTitle());
                    System.out.println("Transaction Count: " + book.getTransactionCount());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

    private void mostActiveUsersReport() {
        System.out.println("Enter the limit for the number of active users:");
        try {
            int limit = Integer.parseInt(reader.readLine());

            List<User> activeUsers = userDao.getMostActiveUsers(limit);

            if (activeUsers.isEmpty()) {
                System.out.println("No active users found.");
            } else {
                System.out.println("Most Active Users:");
                for (User user : activeUsers) {
                    System.out.println("User ID: " + user.getId());
                    System.out.println("User Name: " + user.getName());
                    System.out.println("Transaction Count: " + user.getTransactionCount());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }
}
