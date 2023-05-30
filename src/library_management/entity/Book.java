package library_management.entity;


import java.util.List;

public class Book {

    public static final String validateISBN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    private int id ;
    private String title;
    private String author;
    private int author_id;
    private int publicationYear;
    private String ISBN;
    private int stock = 1;

    private int transactionCount = 0;




    public Book(int id, String title, int author_id, int publicationYear, String ISBN, int stock) {
            this.id = id;
            this.title = title;
            this.author_id = author_id;
            this.publicationYear = publicationYear;
            this.ISBN = ISBN;
            this.stock = stock;

    }

    public Book(String title, int author_id, int publicationYear, String ISBN) {
        this.title = title;
        this.author_id = author_id;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }


    // Getters and Setters


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

        public void setAuthorId(int author_id) {
            this.author_id = author_id;
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
            this.ISBN = ISBN;
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

