package library_management.util;

import java.util.regex.Pattern;

public class Constants {
    public static final String URL = "jdbc:mysql://localhost/data_base";
    public static final String URL_TEST = "jdbc:mysql://localhost/test_data_base";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "8888888888888";
    public static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

}
