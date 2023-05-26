package library_management;

import library_management.Dao.AuthorDao;
import library_management.Dao.TransactionDao;
import library_management.Dao.UserDao;
//import library_management.impl.AuthorDaoImpl;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
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
    private BookDaoImpl bookDao;
    private AuthorDao authorDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private Scanner scanner;

    public TerminalInterface() {
        scanner = new Scanner(System.in);

        // Create a database connection
        try {

            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

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

        System.out.println("Enter the author_id of the book:");
        int author_id = readIntInput();

        System.out.println("Enter the publication year of the book:");
        int publicationYear = readIntInput();

        System.out.println("Enter the ISBN of the book:");
        String isbn = readStringInput();

        // Validate the ISBN using regex
        if (!isbn.matches(ISBN_REGEX)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        // Create a new Book object with the user-provided details
        Book book = new Book(title, author_id, publicationYear, isbn);

        // Call the addBook method of the BookDao instance to add the book to the database
        if (bookDao.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add the book.");
        }
    }


    private void updateBook() {
        System.out.println("Enter the ID of the book to update:");
        int bookId = readIntInput();

        // Retrieve the book from the database using the bookId
          Book bookToUpdate = bookDao.getBookById(bookId);
           if (bookToUpdate == null) {
           System.out.println("Book not found.");
           return;
        }

        System.out.println("Enter the new title of the book (or leave blank to keep the existing title):");
        String newTitle = readStringInput();
        if (!newTitle.isEmpty()) {
            bookToUpdate.setTitle(newTitle);
        }

        System.out.println("Enter the new author_id of the book (or leave blank to keep the existing author):");
        String newAuthor = readStringInput();
        if (!newAuthor.isEmpty()) {
            bookToUpdate.setAuthor(newAuthor);
        }

        System.out.println("Enter the new publication year of the book (or enter 0 to keep the existing year):");
        int newPublicationYear = readIntInput();
        if (newPublicationYear != 0) {
            bookToUpdate.setPublicationYear(newPublicationYear);
        }

        System.out.println("Enter the new ISBN of the book (or leave blank to keep the existing ISBN):");
        String newISBN = readStringInput();
        if (!newISBN.isEmpty()) {
            // Validate the new ISBN using regex
            if (!newISBN.matches(ISBN_REGEX)) {
                System.out.println("Invalid ISBN format. The book will not be updated.");
                return;
            }
            bookToUpdate.setISBN(newISBN);
        }

        // Call the updateBook method of the BookDao instance to update the book in the database
        if (bookDao.updateBook(bookToUpdate)) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Failed to update the book.");
        }
    }




    private void removeBook() {
        System.out.println("Enter the ID of the book to remove:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character


        // Remove the book
        boolean removed = bookDao.removeBook(bookId);
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Failed to remove the book.");
        }
    }


    private void addAuthor() {
        System.out.println("Enter the name of the author:");
        String name = readStringInput();

        System.out.println("Enter the birth year of the author:");
        int birthYear = readIntInput();

        // Create a new Author object with the user-provided details
        Author author = new Author(name, birthYear);

        // Call the addAuthor method of the AuthorDao instance to add the author to the database
        authorDao.addAuthor(author);

    }

    private void updateAuthor() {
        System.out.println("Enter the ID of the author to update:");
        int authorId = readIntInput();

        // Check if the author exists
        Author existingAuthor = authorDao.getAuthorById(authorId);
        if (existingAuthor == null) {
            System.out.println("Author not found.");
            return;
        }

        System.out.println("Enter the updated name of the author:");
        String updatedName = readStringInput();

        System.out.println("Enter the updated birth year of the author:");
        int updatedBirthYear = readIntInput();

        // Create a new Author object with the updated details
        Author updatedAuthor = new Author(updatedName, updatedBirthYear);
        updatedAuthor.setId(authorId);


    }


    private void removeAuthor() {
        System.out.println("Enter the ID of the author to remove:");
        int authorId = readIntInput();

        // Check if the author exists
        Author author = authorDao.getAuthorById(authorId);
        if (author == null) {
            System.out.println("Author with ID " + authorId + " does not exist.");
            return;
        }
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
        System.out.println("Select the type of report to generate:");
        System.out.println("1. All Authors");
        System.out.println("2. All Books");
        System.out.println("3. All Users");
        System.out.println("4. All Transactions");
        System.out.print("\nEnter your choice: ");
        int choice = readIntInput();

        switch (choice) {
            case 1:
                getAllAuthors();
                break;
            case 2:
                getAllBooks();
                break;
            case 3:
                userDao.getAllUsers();
                break;
            case 4:
                transactionDao.getAllTransactions();
                break;
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
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Publication Year: " + book.getPublicationYear());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println();
            }
        }
    }

    private boolean validateISBN(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    private void exit() {
        scanner.close();
    }
}


