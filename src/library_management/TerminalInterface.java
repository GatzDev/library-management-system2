package library_management;

import library_management.controller.AuthorController;
import library_management.controller.TransactionController;
import library_management.controller.UserController;
//import library_management.impl.AuthorDaoImpl;
import library_management.model.Book;
import library_management.controllerImpl.BookControllerImpl;
//import library_management.impl.TransactionDaoImpl;
//import library_management.impl.UserDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class TerminalInterface {
    private static final String URL = "jdbc:mysql://localhost/data_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD ="L31mz40123!";
    private static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);
    private BookControllerImpl bookDao;
    private AuthorController authorDao;
    private UserController userDao;
    private TransactionController transactionDao;
    private Scanner scanner;

    public TerminalInterface() {
        scanner = new Scanner(System.in);

        // Create a database connection
        try {

            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            bookDao = new BookControllerImpl(connection);


          //  authorDao = new AuthorDaoImpl(connection);
           // userDao = new UserDaoImpl(connection);
           // transactionDao = new TransactionDaoImpl(connection);

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();

        }
    }

    public void run() {
        System.out.println("Welcome to the Library System!");


        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("\n1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Author");
            System.out.println("5. Update Author");
            System.out.println("6. Remove Author");
            System.out.println("7. Add User");
            System.out.println("8. Update User");
            System.out.println("9. Remove User");
            System.out.println("10. Search Books");
            System.out.println("11. Display Available Books");
            System.out.println("12. Borrow Book");
            System.out.println("13. Return Book");
            System.out.println("14. Generate Reports");
            System.out.println("15. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = readIntInput();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    addAuthor();
                    break;
                case 5:
                    updateAuthor();
                    break;
                case 6:
                    removeAuthor();
                    break;
                case 7:
                    addUser();
                    break;
                case 8:
                    updateUser();
                    break;
                case 9:
                    removeUser();
                    break;
                case 10:
                    searchBooks();
                    break;
                case 11:
                    displayAvailableBooks();
                    break;
                case 12:
                    borrowBook();
                    break;
                case 13:
                    returnBook();
                    break;
                case 14:
                    generateReports();
                    break;
                case 15:
                    exit();
                    System.out.println("Thank you for using the Library System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int readIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readStringInput() {
        return scanner.nextLine();
    }



    private void addBook() {
        System.out.println("Enter the title of the book:");
        String title = readStringInput();

        System.out.println("Enter the author of the book:");
        String author = readStringInput();

        System.out.println("Enter the publication year of the book:");
        int publicationYear = readIntInput();

        System.out.println("Enter the ISBN of the book:");
        String isbn = readStringInput();

        // Validate the ISBN using regular expression
        while (!validateISBN(isbn)) {
            System.out.println("Invalid ISBN. Please enter a valid ISBN:");
            isbn = readStringInput();
        }

        // Create a new Book object with the user-provided details
        Book book = new Book( title, author, publicationYear, isbn);

        // Call the addBook method of the BookDao instance
        bookDao.addBook(book);
    }


    private void updateBook() {
        // Implementation for updating a book
    }

    private void removeBook() {
        // Implementation for removing a book
    }

    private void addAuthor() {
        // Implementation for adding an author
    }

    private void updateAuthor() {
        // Implementation for updating an author
    }

    private void removeAuthor() {
        // Implementation for removing an author
    }

    private void addUser() {
        // Implementation for adding a user
    }

    private void updateUser() {
        // Implementation for updating a user
    }

    private void removeUser() {
        // Implementation for removing a user
    }

    private void searchBooks() {
        System.out.println("Enter a keyword to search for books:");
        String keyword = readStringInput();

        // Call the searchBooks() method of the BookDao instance to search for books
        List<Book> books = bookDao.searchBooks(keyword);

        if (books.isEmpty()) {
            System.out.println("No books found matching the keyword.");
        } else {
            System.out.println("Books matching the keyword:");
            for (Book book : books) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    private void displayAvailableBooks() {
        // Implementation for displaying available books
    }

    private void borrowBook() {
        // Implementation for borrowing a book
    }

    private void returnBook() {
        // Implementation for returning a book
    }

    private void generateReports() {
        // Implementation for generating reports
    }

    private boolean validateISBN(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    private void exit() {
        scanner.close();
    }
}


