package library_management.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction extends BaseEntity {
    private User user;
    private Book book;
    private final LocalDate borrowingDate;
    private LocalDate returnDate;


    public Transaction(User user, Book book, LocalDate borrowingDate, LocalDate returnDate) {
        this.user = user;
        this.book = book;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public Transaction( LocalDate borrowingDate, LocalDate returnDate) {
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public Transaction(int transactionId, User user, Book book, LocalDate borrowingDate, LocalDate returnDate) {
        setId(transactionId);
        this.user = user;
        this.book = book;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public User getUser() {
        return this.user;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return user.equals(that.user) && book.equals(that.book) && Objects.equals(borrowingDate, that.borrowingDate) && Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book, borrowingDate, returnDate);
    }
}

