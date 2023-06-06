package library_management.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction extends BaseEntity{
    private int userId;
    private int bookId;
    private User user;
    private Book book;
    private LocalDate borrowingDate;
    private LocalDate returnDate;


    public Transaction(int id, User user, Book book, LocalDate borrowingDate, LocalDate returnDate) {
        super(id);
        this.user = user;
        this.book = book;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }


    public Transaction(int id, int userId, int bookId, LocalDate borrowingDate, LocalDate returnDate) {
        super(id);
        this.userId = userId;
        this.bookId = bookId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public Transaction( int userId, int bookId, LocalDate borrowingDate, LocalDate returnDate) {
        super(0);
        this.userId = userId;
        this.bookId = bookId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public int getUserId() {
        return this.userId;
    }


    public int getBookId() {
        return this.bookId;
    }


    public int getId() {
        return this.id;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Transaction other = (Transaction) obj;
        return id == other.id &&
                userId == other.userId &&
                bookId == other.bookId &&
                borrowingDate.equals(other.borrowingDate) &&
                returnDate.equals(other.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, bookId, borrowingDate, returnDate);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                '}';
    }



}

