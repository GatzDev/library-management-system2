package library_management.controller;

import library_management.model.Author;

import java.util.List;

public interface AuthorController {
    public void addAuthor(Author author);
    public void updateAuthor(Author author);
    public void removeAuthor(int authorId);
    Author getAuthorById(int authorId);
    public List<Author> getAllAuthors();
}
