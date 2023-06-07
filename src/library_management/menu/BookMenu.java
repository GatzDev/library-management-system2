package library_management.menu;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.impl.BookDaoImpl;
import library_management.util.Constants;
import library_management.util.Input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static library_management.util.Input.readIntInput;

public class BookMenu {

    private BufferedReader reader;
    private BookDao bookDao;
    private AuthorDao authorDao;

    public BookMenu(AuthorDao authorDao) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.authorDao = authorDao;

        try {
            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
            bookDao = new BookDaoImpl(connection);
        } catch (
                SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }

    public void bMenu() {
        System.out.println("--- Book Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Search Book");
        System.out.println("5. Available Book");
        System.out.println("6. Return to Previous Menu");
        System.out.print("Enter your choice: ");

        int choice = readIntInput(reader);

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
                searchBooks();
                break;
            case 5:
                availableBooks();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addBook() {
        System.out.println("Enter the title of the book:");
        String title = Input.readStringInput(reader);

        System.out.println("Enter the author_id of the book:");
        int author_id = Input.readIntInput(reader);

        System.out.println("Enter the publication year of the book:");
        int publicationYear = Input.readIntInput(reader);

        System.out.println("Enter the ISBN of the book:");
        String isbn = Input.readStringInput(reader);

        // Validate the ISBN
        if (!isbn.matches(Constants.ISBN_REGEX)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        Book book = new Book(title, author_id, publicationYear, isbn);

        if (bookDao.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add the book.");
        }
    }

    private void updateBook() {
        System.out.println("Enter the ID of the book to update:");
        int bookId = Input.readIntInput(reader);

        // Take the book from the database using the bookId
        Book bookToUpdate = bookDao.getBookById(bookId);
        if (bookToUpdate == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Enter the new title of the book (or leave blank to keep the existing title):");
        String newTitle = Input.readStringInput(reader);
        if (!newTitle.isEmpty()) {
            bookToUpdate.setTitle(newTitle);
        }

        System.out.println("Enter the new author_id of the book (or leave blank to keep the existing author):");
        String newAuthor = Input.readStringInput(reader);
        if (!newAuthor.isEmpty()) {
            bookToUpdate.setAuthor(newAuthor);
        }

        System.out.println("Enter the new publication year of the book (or enter 0 to keep the existing year):");
        int newPublicationYear = Input.readIntInput(reader);
        if (newPublicationYear != 0) {
            bookToUpdate.setPublicationYear(newPublicationYear);
        }

        System.out.println("Enter the new ISBN of the book (or leave blank to keep the existing ISBN):");
        String newISBN = Input.readStringInput(reader);
        if (!newISBN.isEmpty()) {

            if (!newISBN.matches(Constants.ISBN_REGEX)) {
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
        int bookId = Input.readIntInput(reader);

        boolean removed = bookDao.removeBook(bookId);
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Failed to remove the book.");
        }
    }

    private void searchBooks() {
        System.out.println("Enter a keyword to search for books:");
        String keyword = Input.readStringInput(reader);

        // Call the searchBooks() method of the BookDao instance to search for books
        List<Book> books = bookDao.searchBooks(keyword);

        if (books.isEmpty()) {
            System.out.println("No books found matching the keyword.");
        } else {
            System.out.println("Books matching the keyword:");
            for (Book book : books) {
                Author author = authorDao.getAuthorById(book.getAuthorId());

                book.setAuthor(author.getName());

                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    private void availableBooks() {

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


}