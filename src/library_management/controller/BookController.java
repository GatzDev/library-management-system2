package library_management.controller;

import library_management.model.Book;

import java.util.List;

public interface BookController {
    public void addBook(Book book);
    public void updateBook(Book book);
    public void removeBook(int bookId);
    public List<Book> searchBooks(String keyword);
    public List<Book> getAllBooks();
    public Book getBookById(int bookId);

    }
