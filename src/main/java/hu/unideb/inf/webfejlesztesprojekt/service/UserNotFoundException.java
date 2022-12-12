package hu.unideb.inf.webfejlesztesprojekt.service;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
        super(message);
    }
}