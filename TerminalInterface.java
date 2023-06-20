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
import library_management.util.DatabaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static library_management.util.Input.readIntInput;


public class TerminalInterface {

    private BookDaoImpl bookDao;
    private AuthorDao authorDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private BufferedReader reader;


    public TerminalInterface() {
        reader = new BufferedReader(new InputStreamReader(System.in));

        DatabaseManager.connect();

        Connection connection = DatabaseManager.getConnection();

        bookDao = new BookDaoImpl(connection);
        authorDao = new AuthorDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
        transactionDao = new TransactionDaoImpl(connection);
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
                    AuthorMenu authorMenu = new AuthorMenu();
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
        displayUsers();

        try {
            System.out.println("Enter the ID of the user:");
            int userId = Integer.parseInt(reader.readLine());

            User user = userDao.getUserById(userId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            List<Book> books = bookDao.getAllBooks();
            System.out.println("--- List of Authors ---");
            for (Book book : books) {
                System.out.println("ID: " + book.getId() + ", Name: " + book.getTitle() + ", Stock:" + book.getStock() );
            }

            System.out.println("Enter the ID of the book to borrow:");
            int bookId = Integer.parseInt(reader.readLine());

            Book book = bookDao.getBookById(bookId);

            if (book == null) {
                System.out.println("Book not found.");
                return;
            }

            // Check the book is already borrowed
            if (book.getStock() <= 0) {
                System.out.println("The book is not available for borrowing.");
                return;
            }

            LocalDate borrowingDate = LocalDate.now();
           // LocalDate returnDate = null;

            Transaction transaction = new Transaction(user, book, borrowingDate, null);
            transactionDao.addTransaction(transaction);

            book.setStock(book.getStock() - 1);
            bookDao.updateBook(book);

            System.out.println("Book borrowed successfully!");
        } catch (IOException e) {
            System.out.println("Failed to borrow.");
            e.printStackTrace();
        }
    }


    private void returnBook() {
        displayUsers();

        try {
            System.out.println("Enter the ID of the user:");
            int userId = Integer.parseInt(reader.readLine());

            User user = userDao.getUserById(userId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            List<Book> borrowedBooks = transactionDao.getBorrowedBooksByUser(user.getId());

            if (borrowedBooks.isEmpty()) {
                System.out.println("No borrowed books found for this user.");
                return;
            }

            System.out.println("--- List of Borrowed Books ---");
            for (Book book : borrowedBooks) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle());
            }

            System.out.println("Enter the ID of the book to return:");
            int bookId = Integer.parseInt(reader.readLine());

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

            transaction.setReturnDate(LocalDate.now());
            transactionDao.updateTransaction(transaction);

            book.setStock(book.getStock() + 1);
            bookDao.updateBook(book);

            System.out.println("Book returned successfully!");
        } catch (IOException e) {
            System.out.println("Failed to read user input. Please try again.");
            e.printStackTrace();
        }
    }

    private void displayUsers() {
        List<User> users = userDao.getAllUsers();

        System.out.println("--- List of Users ---");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }
    }

    private void exit() {
        System.out.println("Exiting the application...");
        System.out.println("Thank you for using the Library System!");
        System.exit(0);
    }
}


