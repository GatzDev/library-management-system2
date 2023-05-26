package library_management.entity;

import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    public User(int id, String name, String email, List<Book> borrowedBooks) {
        this.id =id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
    }

    public User(int id, String name, String email) {
        this.id =id;
        this.name = name;
        this.email = email;

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowedBooks() {
        return this.borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
