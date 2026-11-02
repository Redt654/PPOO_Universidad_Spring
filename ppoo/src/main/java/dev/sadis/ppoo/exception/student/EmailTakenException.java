package dev.sadis.ppoo.exception.student;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String message) {
        super(message);
    }
}
