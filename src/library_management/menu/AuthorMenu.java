package library_management.menu;
import library_management.dao.AuthorDao;
import library_management.dao.BookDao;
import library_management.entity.Author;
import library_management.impl.AuthorDaoImpl;
import library_management.util.Constants;
import library_management.util.Input;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class AuthorMenu {
    private  BufferedReader reader;
    private AuthorDao authorDao;
    private BookDao bookDao;

    public  AuthorMenu(BookDao bookDao) {
        this.bookDao = bookDao;

        reader = new BufferedReader(new InputStreamReader(System.in));


        // Create a database connection
        try {
            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
            authorDao = new AuthorDaoImpl(connection);
        } catch (
                SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();

        }
    }

    public  void aMenu() {
        System.out.println("--- Author Menu ---");
        System.out.println("1. Add Author");
        System.out.println("2. Update Author");
        System.out.println("3. Delete Author");
        System.out.println("4. Search Author");
        System.out.print("Enter your choice: ");

        int choice = Input.readIntInput(reader);

        switch (choice) {
            case 1:
                addAuthor();
                break;
            case 2:
                updateAuthor();
                break;
            case 3:
                removeAuthor();
                break;
            case 4:
                searchAuthor();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addAuthor() {
        System.out.println("Enter the name of the author:");
        String name = Input.readStringInput(reader);

        System.out.println("Enter the birth year of the author:");
        int birthYear = Input.readIntInput(reader);

        // Create a new Author object with the user-provided details
        Author author = new Author(name, birthYear);

        // Call the addAuthor method of the AuthorDao instance to add the author to the database
        authorDao.addAuthor(author);
    }

    private void updateAuthor() {
        System.out.println("Enter the ID of the author to update:");
        int authorId = Input.readIntInput(reader);

        // Check if the author exists
        Author existingAuthor = authorDao.getAuthorById(authorId);
        if (existingAuthor == null) {
            System.out.println("Author not found.");
            return;
        }

        System.out.println("Enter the new name of the author (or leave blank to keep the existing name):");
        String updatedName = Input.readStringInput(reader);

        if (!updatedName.isBlank()) {
            existingAuthor.setName(updatedName);
        }


        System.out.println("Enter the new birth year of the author (or leave blank to keep the existing birth year):");
        int updatedBirthYear = Input.readIntInput(reader);

        if (updatedBirthYear != 0) {
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
        int authorId = Input.readIntInput(reader);

        Author author = authorDao.getAuthorById(authorId);
        if (author == null) {
            System.out.println("Author with ID " + authorId + " does not exist.");
            return;
        }

        boolean removed = authorDao.removeAuthor(authorId);
        if (removed) {
            System.out.println("Author removed successfully.");
        } else {
            System.out.println("Failed to remove the author.");
        }
    }

    private void searchAuthor() {
        System.out.println("Enter a keyword to search for authors:");
        String keyword = Input.readStringInput(reader);

        // Call the searchAuthor() method of the AuthorDao instance to search for authors
        List<Author> authors = authorDao.searchAuthor(keyword);

        if (authors.isEmpty()) {
            System.out.println("No authors found matching the keyword.");
        } else {
            System.out.println("Authors matching the keyword:");
            for (Author author : authors) {
                int books = bookDao.getBooksByAuthorId(author.getId());
                System.out.println(author.getName() + " (" + books + " books)");
            }
        }
    }
}

