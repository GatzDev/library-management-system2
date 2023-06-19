package library_management.entity;


public class Author extends BaseEntity {
    private String name;
    private int birthYear;
    private int bookCount;


    public Author( String name, int birthYear) {
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
    public int getBookCount(){
        return bookCount;
    }
    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
    @Override
    public String toString() {
        return "Author ID: " + getId() + ", Name: " + name + ", Birth Year: " + birthYear;
    }
}

