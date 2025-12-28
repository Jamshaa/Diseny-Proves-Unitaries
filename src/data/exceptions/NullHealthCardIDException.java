package data.exceptions;

public class NullHealthCardIDException extends RuntimeException {
    public NullHealthCardIDException(String message) {
        super(message);
    }
}
