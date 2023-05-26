package library_management.entity;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private User user;
    private Book book;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

    public Transaction(int id, User user, Book book, LocalDate borrowingDate, LocalDate returnDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public Transaction(int id, int userId, int bookId, LocalDate borrowingDate, LocalDate returnDate) {

    }

    public int getId() {
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

