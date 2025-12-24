package data.exceptions;

public class InvalidProductIDFormatException extends RuntimeException {
    public InvalidProductIDFormatException(String message) {
        super(message);
    }
}
