package library_management.Dao;

import library_management.entity.Author;

import java.util.List;

public interface AuthorDao {
    public void addAuthor(Author author);
    public boolean updateAuthor(Author author);
    public void removeAuthor(int authorId);
    public List<Author> getAllAuthors();

    Author getAuthorById(int authorId);
}
