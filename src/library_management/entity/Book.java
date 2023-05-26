package library_management.entity;


public class Book {

    public static final String validateISBN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    private int id ;
    private String title;
    private String author;
    private int author_id;
    private int publicationYear;
    private String ISBN;
    private boolean stock;


    public Book(int id, String title, int author_id, int publicationYear, String ISBN, boolean stock) {
            this.id = id;
            this.title = title;
            this.author_id = author_id;
            this.publicationYear = publicationYear;
            this.ISBN = ISBN;
            this.stock = true;
    }

    public Book(String title, int author_id, int publicationYear, String ISBN) {
        this.title = title;
        this.author_id = author_id;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }


    // Getters and Setters
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

        public void setAuthorId(int author) {
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

        public boolean isAvailable() {
            return stock;
        }

        public void setAvailability(boolean stock) {
            this.stock = stock;
        }


    public int getAuthorId() {
        return author_id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}

