package library_management.Dao;

import library_management.entity.Book;

import java.util.List;

public interface BookDao {
    public boolean addBook(Book book);
    public boolean updateBook(Book book);
    public boolean removeBook(int bookId);
    public List<Book> searchBooks(String keyword);
    public List<Book> getAllBooks();
    public Book getBookById(int bookId);


    }
