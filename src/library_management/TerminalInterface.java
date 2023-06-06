package library_management;

import library_management.dao.AuthorDao;
import library_management.dao.TransactionDao;
import library_management.dao.UserDao;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
import library_management.menu.AuthorMenu;
import library_management.menu.BookMenu;
import library_management.menu.ReportsMenu;
import library_management.menu.UserMenu;
import library_management.util.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import static library_management.util.Input.readIntInput;


public class TerminalInterface {

    private BookDaoImpl bookDao;
    private AuthorDao authorDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private BufferedReader reader;



    public TerminalInterface() {
        reader = new BufferedReader(new InputStreamReader(System.in));


        // Create a database connection
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

    public void run() {
        System.out.println("Welcome to the Library System!");


        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("\n1. Book Menu");
            System.out.println("2. Author Menu");
            System.out.println("3. User Menu");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Generate Reports");
            System.out.println("7. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = readIntInput(reader);

            switch (choice) {
                case 1:
                    BookMenu bookMenu = new BookMenu(authorDao);
                    bookMenu.bMenu();
                    break;
                case 2:
                    AuthorMenu authorMenu = new AuthorMenu(bookDao);
                    authorMenu.aMenu();
                    break;
                case 3:
                    UserMenu userMenu = new UserMenu();
                    userMenu.uMenu();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    ReportsMenu reportsMenu = new ReportsMenu();
                    reportsMenu.generateReports();
                    break;
                case 7:
                    exit();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void borrowBook() {
        try {
            System.out.println("Enter the ID of the user:");
            int userId = Integer.parseInt(reader.readLine());

            // Retrieve the existing user by ID
            User user = userDao.getUserById(userId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.println("Enter the ID of the book to borrow:");
            int bookId = Integer.parseInt(reader.readLine());

            // Retrieve the existing book by ID
            Book book = bookDao.getBookById(bookId);

            if (book == null) {
                System.out.println("Book not found.");
                return;
            }

            // Check if the book is already borrowed
            if (book.getStock() == 0) {
                System.out.println("The book is not available for borrowing.");
                return;
            }

            // Create a new transaction
            LocalDate borrowingDate = LocalDate.now();
            LocalDate returnDate = borrowingDate.plusDays(29);
            Transaction transaction = new Transaction(userId, bookId, borrowingDate, returnDate);
            transactionDao.addTransaction(transaction);

            // Update the book availability
            book.setStock(0); // Assuming 0 represents a borrowed book
            bookDao.updateBook(book);

            System.out.println("Book borrowed successfully!");
        } catch (IOException e) {
            System.out.println("Failed to read user input.");
            e.printStackTrace();
        }
    }

        private void returnBook() {
            try {
                System.out.println("Enter the ID of the user:");
                int userId = Integer.parseInt(reader.readLine());

                // Retrieve the existing user by ID
                User user = userDao.getUserById(userId);

                if (user == null) {
                    System.out.println("User not found.");
                    return;
                }

                System.out.println("Enter the ID of the book to return:");
                int bookId = Integer.parseInt(reader.readLine());

                // Retrieve the existing book by ID
                Book book = bookDao.getBookById(bookId);

                if (book == null) {
                    System.out.println("Book not found.");
                    return;
                }

                // Check if the book is borrowed by the user
                Transaction transaction = transactionDao.getTransactionByUserAndBook(user.getId(), book.getId());

                if (transaction == null) {
                    System.out.println("The book is not borrowed by the user.");
                    return;
                }

                // Update the transaction return date
                transaction.setReturnDate(LocalDate.now());
                transactionDao.updateTransaction(transaction);

                // Update the book availability
                book.setStock(1);
                bookDao.updateBook(book);

                System.out.println("Book returned successfully!");
            } catch (IOException e) {
                System.out.println("Failed to read user input.");
                e.printStackTrace();
            }
        }

    private void exit() {
        System.out.println("Exiting the application...");
        System.out.println("Thank you for using the Library System!");
        System.exit(0);
    }
}


