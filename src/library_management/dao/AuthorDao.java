package library_management.dao;

import library_management.entity.Author;

import java.util.List;

public interface AuthorDao {
    Author addAuthor(Author author);

    boolean updateAuthor(Author author);

    boolean removeAuthor(int authorId);

    List<Author> getAllAuthors();

    Author getAuthorById(int authorId);

    List<Author> getMostProlificAuthors(int limit);

    List<Author> searchAuthor(String keyword);

    List<Author> getAllAuthorsWithId();

    boolean authorExistsInDatabase(Author author);

}
