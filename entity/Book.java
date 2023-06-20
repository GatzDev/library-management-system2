package library_management.entity;

public class Book extends BaseEntity{

    private String title;
    private Author author;
    private int publicationYear;
    private String ISBN;
    private int stock;
    private int transactionCount = 0;


    public Book( String title, Author author, int publicationYear, String ISBN, int stock) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.stock = stock;
    }

    public Book(String title, Author author, int publicationYear, String ISBN) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public Book( String title) {
        this.title = title;
    }

    public Book(int id, String title, Author author, int publicationYear, String ISBN) {
        setId(id);
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public Book(int id, String title, Author author, int publicationYear, String ISBN, int stock) {
        setId(id);
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.stock = stock;

    }


    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
            this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book ID: " + this.getId() +
                ", Title: " + title +
                ", Author ID: " +  author.getId()  +
                ", Publication Year: " + publicationYear +
                ", ISBN: " + (ISBN != null ? ISBN : "N/A") +
                ", Stock: " + this.getStock();
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
        return  this.getId() == other.getId() &&
                title.equals(other.title) &&
                author == other.author &&
                publicationYear == other.publicationYear &&
                ISBN.equals(other.ISBN) &&
                stock == other.stock;
    }
}

