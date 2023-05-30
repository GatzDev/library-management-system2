package library_management;

import library_management.Dao.AuthorDao;
import library_management.Dao.TransactionDao;
import library_management.Dao.UserDao;
//import library_management.impl.AuthorDaoImpl;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.entity.Transaction;
import library_management.entity.User;
import library_management.impl.AuthorDaoImpl;
import library_management.impl.BookDaoImpl;
import library_management.impl.TransactionDaoImpl;
import library_management.impl.UserDaoImpl;
//import library_management.impl.TransactionDaoImpl;
//import library_management.impl.UserDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class TerminalInterface {
    private static final String URL = "jdbc:mysql://localhost/data_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "L31mz40123!";
    private static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);
    private BookDaoImpl bookDao;
    private AuthorDao authorDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private Scanner scanner;
    private BufferedReader reader;


    public TerminalInterface() {
        reader = new BufferedReader(new InputStreamReader(System.in));


        // Create a database connection
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

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
            System.out.println("10. Search Book");
            System.out.println("11. Search User");
            System.out.println("12. Display Available Books");
            System.out.println("13. Borrow Book");
            System.out.println("14. Return Book");
            System.out.println("15. Generate Reports");
            System.out.println("16. Exit");
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
                    searchUsers();
                    break;
                case 12:
                    displayAvailableBooks();
                    break;
                case 13:
                    borrowBook();
                    break;
                case 14:
                    returnBook();
                    break;
                case 15:
                    generateReports();
                    break;
                case 16:
                    exit();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int readIntInput() {
        while (true) {
            try {
                String input = reader.readLine();
                return Integer.parseInt(input);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readStringInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Please try again.");
            return null;
        }
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
        int bookId = readIntInput();

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

        System.out.println("Enter the new name of the author (or leave blank to keep the existing name):");
        String updatedName = readStringInput();

        if (!updatedName.isBlank()) {
            existingAuthor.setName(updatedName);
        }


        System.out.println("Enter the new birth year of the author (or leave blank to keep the existing birth year):");
        int updatedBirthYear = readIntInput();

        if (updatedBirthYear != 0 ){
            existingAuthor.setBirthYear(updatedBirthYear);
        }


        // Create a new Author object with the updated details
        Author updatedAuthor = new Author(updatedName, updatedBirthYear);
        updatedAuthor.setId(authorId);


        // Update the author in the database
        boolean updated = authorDao.updateAuthor(updatedAuthor);
        if (updated) {
            System.out.println("Author updated successfully.");
        } else {
            System.out.println("Failed to update the author.");
        }
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

        // Remove the author
        boolean removed = authorDao.removeAuthor(authorId);
        if (removed) {
            System.out.println("Author removed successfully.");
        } else {
            System.out.println("Failed to remove the author.");
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
        try{
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
    } catch(
    IOException e)
    {
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
                // Retrieve the author for the book using the author ID
                Author author = authorDao.getAuthorById(book.getAuthorId());

                // Set the author name for the book
                book.setAuthor(author.getName());

                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
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


    private void displayAvailableBooks() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nAvailable Books:");
        List<Book> availableBooks = bookDao.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : availableBooks) {
                System.out.println(book.getTitle());
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



    private void generateReports() {
        System.out.println("Select the type of report to generate:");
        System.out.println("1. All Authors");
        System.out.println("2. All Books");
        System.out.println("3. All Users");
        System.out.println("4. All Transactions");
        System.out.println("5. Most Prolific Authors");
        System.out.println("6. Most Popular Books");
        System.out.println("7. Most Active Users");
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
        List<Transaction> transactions = transactionDao.getAllTransactions();

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






    private void exit() {
        System.out.println("Exiting the application...");
        System.out.println("Thank you for using the Library System!");
        System.exit(0);
    }
}


