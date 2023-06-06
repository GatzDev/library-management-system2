package library_management.entity;

import library_management.util.Constants;

public class Book extends BaseEntity{

    private String title;
    private String author;
    private int author_id;
    private int publicationYear;
    private String ISBN;
    private int stock = 1;
    private int transactionCount = 0;


    public Book(int id, String title, int author_id, int publicationYear, String ISBN, int stock) {
        super(id);
        this.title = title;
        this.author_id = author_id;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.stock = stock;
    }

    public Book(String title, int author_id, int publicationYear, String ISBN) {
        super(0);
        this.title = title;
        this.author_id = author_id;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public Book(int id, String title) {
        super(id);
        this.title = title;
    }

    public Book(int bookId, String title, int authorId) {
        super(0);
    }


    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return author_id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        if (Constants.ISBN_PATTERN.matcher(ISBN).matches()){
        this.ISBN = ISBN;
        } else {
            throw new IllegalArgumentException("Invalid ISBN format.");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return "Book ID: " + id +
                ", Title: " + title +
                ", Author ID: " + author_id +
                ", Publication Year: " + publicationYear +
                ", ISBN: " + ISBN +
                ", Stock: " + stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return id == other.id &&
                title.equals(other.title) &&
                author_id == other.author_id &&
                publicationYear == other.publicationYear &&
                ISBN.equals(other.ISBN) &&
                stock == other.stock;
    }


}

