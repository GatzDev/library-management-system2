package library_management.util;

import java.util.regex.Pattern;

public class Constants {
    public static final String URL = "jdbc:mysql://localhost/data_base";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "L31mz40123!";
    public static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    public static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);

    private Constants() {
        // Private constructor to prevent instantiation of the class
    }
}
