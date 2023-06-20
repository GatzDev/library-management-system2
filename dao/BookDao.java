package library_management.dao;

import library_management.entity.Book;

import java.util.List;

public interface BookDao {
    boolean addBook(Book book);

    boolean updateBook(Book book);

    boolean removeBook(int bookId);

    List<Book> searchBooks(String keyword, AuthorDao authorDao);

    List<Book> getAllBooks();

    Book getBookById(int bookId);

    List<Book> getAvailableBooks();

    List<Book> getMostPopularBooks(int limit);

    int getBooksByAuthorId(int id);
}
