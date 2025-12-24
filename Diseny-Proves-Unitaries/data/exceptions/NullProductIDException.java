package data.exceptions;

public class NullProductIDException extends RuntimeException {
    public NullProductIDException(String message) {
        super(message);
    }
}
