package library_management.entity;

import java.util.List;
import java.util.Objects;

public class User extends BaseEntity{

    private String name;
    private String email;
    private List<Book> borrowedBooks;
    private int transactionCount = 0;


    public User(int id, String name, String email, List<Book> borrowedBooks) {
        super(id);
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
    }

    public User(String name, String email) {
        super(0);
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public User(int id, String name) {
        super(id);
        this.name = name;
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




}

