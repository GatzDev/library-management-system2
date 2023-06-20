package library_management.entity;
import java.util.Objects;

public class User extends BaseEntity{

    private String name;
    private String email;
    //private List<Book> borrowedBooks;
    private int transactionCount = 0;


//    public User( String name, String email, List<Book> borrowedBooks) {
//        this.name = name;
//        this.email = email;
//        this.borrowedBooks = borrowedBooks;
//    }

    public User( String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User( String name) {
        this.name = name;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }
    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User otherUser = (User) obj;
        return Objects.equals(name, otherUser.getName()) &&
                Objects.equals(email, otherUser.getEmail());
    }
    @Override
    public String toString() {
        return "User ID: " + getId() + ", Name: " + name + ", Email: " + email;
    }
}

