package library_management.model;

import java.util.List;

public class Author {
    private String name;
    private int birthYear;
    private List<Book> books;

    public Author(String name, int birthYear, List<Book> books) {
        this.name = name;
        this.birthYear = birthYear;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

