package library_management.menu;

import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Author;
import library_management.entity.Book;
import library_management.impl.BookDaoImpl;
import library_management.util.Constants;
import library_management.util.DatabaseManager;
import library_management.util.Input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static library_management.util.Input.readIntInput;

public class BookMenu {
    private BufferedReader reader;
    private BookDao bookDao;
    private AuthorDao authorDao;

    public BookMenu(AuthorDao authorDao) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.authorDao = authorDao;

        DatabaseManager.connect();

        Connection connection = DatabaseManager.getConnection();
        bookDao = new BookDaoImpl(connection);
    }

    public void bMenu() {
        System.out.println("--- Book Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Search Book");
        System.out.println("5. Available Book");
        System.out.println("6. Return to Previous Menu\n");
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

    public Book addBook() {
        System.out.println("Enter the title of the book:");
        String title = Input.readStringInput(reader);

        List<Author> authors = authorDao.getAllAuthorsWithId();
        System.out.println("--- List of Authors ---");
        for (Author author : authors) {
            System.out.println("ID: " + author.getId() + ", Name: " + author.getName());
        }

        System.out.println("Enter the ID of the author:");
        int authorId = Input.readIntInput(reader);

        Author selectedAuthor = null;
        for (Author author : authors) {
            if (author.getId() == authorId) {
                selectedAuthor = author;
                break;
            }
        }

        if (selectedAuthor == null) {
            System.out.println("Author not found.");
            Input.readIntInput(reader);
        }

        System.out.println("Enter the publication year of the book:");
        int publicationYear = Input.readIntInput(reader);

        if (publicationYear >= 1000 || publicationYear <= LocalDate.now().getYear()) {
        }else {
            System.out.println("Please enter a valid year.");
            Input.readIntInput(reader);
        }


        String isbn;
        boolean validISBN = false;
        do {
            System.out.println("Enter the ISBN of the book:");
            isbn = Input.readStringInput(reader);

            if (!isbn.matches(Constants.ISBN_REGEX)) {
                System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            } else {
                validISBN = true;
            }
        } while (!validISBN);


        System.out.println("Enter the stock of the book:");
        int stock = Input.readIntInput(reader);

        if (stock <= 0) {
            System.out.println("Stock must be greater than 0.");
            Input.readIntInput(reader);
        }

        Book book = new Book(title, selectedAuthor, publicationYear, isbn);
        book.setStock(stock);

        boolean added = bookDao.addBook(book);
        if (added) {
            System.out.println("Book added successfully.");
            return book;
        } else {
            System.out.println("Failed to add the book.");
            return book;
        }
    }

    public void updateBook() {
        displayAllBooks();

        System.out.println("Enter the ID of the book to update:");
        int bookId = Input.readIntInput(reader);

        Book bookToUpdate = bookDao.getBookById(bookId);
        if (bookToUpdate == null) {
            System.out.println("Book not found.");
            Input.readIntInput(reader);
        }

        System.out.println("Enter the new title of the book (or leave blank to keep the existing title):");
        String newTitle = Input.readStringInput(reader);
        if (!newTitle.isBlank()) {
            bookToUpdate.setTitle(newTitle);
        }

        List<Author> authors = authorDao.getAllAuthors();
        System.out.println("--- List of Authors ---");
        for (Author author : authors) {
            System.out.println("ID: " + author.getId() + ", Name: " + author.getName());
        }

        System.out.println("Enter the ID of the author:");
        int authorId = Input.readIntInput(reader);

        Author selectedAuthor = null;
        for (Author author : authors) {
            if (author.getId() == authorId) {
                selectedAuthor = author;
                break;
            }
        }

        if (selectedAuthor == null) {
            System.out.println("Invalid author ID.");
            return;
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

        System.out.println("Enter the new stock of the book (or enter -1 to keep the existing stock):");
        int newStock = Input.readIntInput(reader);
        if (newStock != -1) {
            bookToUpdate.setStock(newStock);
        }

        // Update the book in the database
        if (bookDao.updateBook(bookToUpdate)) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Failed to update the book.");
        }
    }

    public void removeBook() {
        displayAllBooks();

        System.out.println("Enter the ID of the book to remove:");
        int bookId = Input.readIntInput(reader);

        boolean removed = bookDao.removeBook(bookId);
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Failed to remove the book.");
        }
    }

    public void searchBooks() {
        System.out.println("Enter a keyword to search for books:");
        String keyword = Input.readStringInput(reader);

        // Call the searchBooks() method of the BookDao instance to search for books
        List<Book> books = bookDao.searchBooks(keyword, authorDao);

        if (books.isEmpty()) {
            System.out.println("No books found matching the keyword.");
        } else {
            System.out.println("Books matching the keyword:");
            for (Book book : books) {
                System.out.println(book.getTitle() + " by " + book.getAuthor().getName());
            }
        }
    }

    public void availableBooks() {

        System.out.println("\n--- Available Books ---");
        List<Book> availableBooks = bookDao.getAvailableBooks();

        printBookList(availableBooks);
    }


    public void displayAllBooks() {
        System.out.println("\n--- List of Books ---");
        List<Book> books = bookDao.getAllBooks();
        printBookList(books);

    }

    public void printBookList(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                System.out.println("ID: " + book.getId() + ", Name: " + book.getTitle());
            }
        }
    }


}