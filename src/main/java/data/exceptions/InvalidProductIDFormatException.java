package data.Exceptions;

public class InvalidProductIDFormatException extends RuntimeException {
    public InvalidProductIDFormatException(String message) {
        super(message);
    }
}
