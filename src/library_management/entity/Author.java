package library_management.entity;

import java.util.List;

public class Author extends BaseEntity {
    private String name;
    private int birthYear;
    private List<Book> books;
    private int bookCount;


    public Author(int id, String name, int birthYear) {
        super(id);
        this.name = name;
        this.birthYear = birthYear;
    }

    public Author(String name, int birthYear) {
        super(0);    //  0 as default ID for a new author
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBookCount(){
        return bookCount;
    }
    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }


}

