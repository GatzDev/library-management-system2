package library_management.entity;

import java.util.List;

public class Author {
    private String name;
    private int birthYear;
    private List<Book> books;
    private int id;

    public Author(String name, int birthYear, List<Book> books, int id) {
        this.name = name;
        this.birthYear = birthYear;
        this.books = books;
        this.id = id;
    }

    public Author(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;

    }

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
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

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
}

