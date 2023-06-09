package library_management.dao;

import library_management.entity.Book;

import java.util.List;

public interface BookDao {
    public boolean addBook(Book book);
    public boolean updateBook(Book book);
    public boolean removeBook(int bookId);
    public List<Book> searchBooks(String keyword, AuthorDao authorDao);
    public List<Book> getAllBooks();
    public Book getBookById(int bookId);
    List<Book> getAvailableBooks();
    public List<Book> getMostPopularBooks(int limit) ;
    int getBooksByAuthorId(int id);
}
