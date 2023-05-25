package library_management.model;


public class Book {

    public static final String validateISBN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    private int bookId ;
    private String title;
    private String author;
    private int publicationYear;
    private String ISBN;
    private boolean availability;



    public Book(int bookId, String title, String author, int publicationYear, String ISBN, boolean availability) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.publicationYear = publicationYear;
            this.ISBN = ISBN;
            this.availability = true;
        }

    public Book(String title, String author, int publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    // Getters and Setters
        public int getBookId() {
            return this.bookId;
        }

        public void setBookId(int bookId) {
        this.bookId = bookId;
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
            this.ISBN = ISBN;
        }

        public boolean isAvailable() {
            return availability;
        }

        public void setAvailability(boolean availability) {
            this.availability = availability;
        }

    
    
    }

